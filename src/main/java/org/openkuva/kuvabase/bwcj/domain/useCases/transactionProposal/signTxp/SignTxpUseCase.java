/*
 * Copyright (c)  2018 One Kuva LLC, known as OpenKuva.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted (subject to the limitations in the disclaimer
 * below) provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *      * Neither the name of the One Kuva LLC, known as OpenKuva.org nor the names of its
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY
 * THIS LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.signTxp;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.IInput;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionProposal;
import org.openkuva.kuvabase.bwcj.domain.utils.CopayersCryptUtils;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.IBitcoreWalletServerAPI;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.pojo.signatures.SignatureRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.openkuva.kuvabase.bwcj.domain.utils.DeriveUtils.deriveChildByPath;
import static org.openkuva.kuvabase.bwcj.domain.utils.transactions.CopayTransactionUtils.buildTx;

public class SignTxpUseCase implements ISignTxpUseCase {
    private final IBitcoreWalletServerAPI bwsApi;
    private final ICredentials credentials;

    public SignTxpUseCase(IBitcoreWalletServerAPI bwsApi, ICredentials credentials) {
        this.bwsApi = bwsApi;
        this.credentials = credentials;
    }

    @Override
    public ITransactionProposal execute(ITransactionProposal txToSign) {
        DeterministicKey xpriv =
                CopayersCryptUtils.derivedXPrivKey(
                        credentials.getSeed(),
                        credentials.getNetworkParameters());

        Map<String, DeterministicKey> derived = new HashMap<>();
        LinkedList<DeterministicKey> privs = new LinkedList<>();

        for (IInput input : txToSign.getInputs()) {
            if (derived.get(input.getPath()) == null) {
                DeterministicKey result = deriveChildByPath(xpriv, input.getPath());
                derived.put(input.getPath(), result);
                privs.push(derived.get(input.getPath()));
            }
        }

        Transaction transaction = buildTx(txToSign);
        List<List<IndexedTransactionSignature>> signaturesLists = new ArrayList<>();
        for (int i = 0; i < privs.size(); i++) {
            signaturesLists.add(
                    getSignatures(
                            transaction,
                            privs.get(i),
                            credentials.getNetworkParameters()));
        }

        return
                bwsApi.postTxProposalsTxIdSignatures(
                        txToSign.getId(),
                        new SignatureRequest(
                                mapSignatures(
                                        sort(
                                                flat(signaturesLists)))));
    }

    private static List<IndexedTransactionSignature> sort(List<IndexedTransactionSignature> toSort) {
        Collections.sort(toSort, (o1, o2) -> Integer.compare(o1.index, o2.index));
        return toSort;
    }

    private static List<IndexedTransactionSignature> flat(List<List<IndexedTransactionSignature>> lists) {
        List<IndexedTransactionSignature> result = new ArrayList<>();
        for (List<IndexedTransactionSignature> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    private static List<String> mapSignatures(List<IndexedTransactionSignature> signatures) {
        List<String> result = new ArrayList<>();
        for (IndexedTransactionSignature signature : signatures) {
            result.add(
                    Utils.HEX.encode(
                            signature.signature.encodeToDER()));

        }
        return result;
    }

    private static List<IndexedTransactionSignature> getSignatures(Transaction transaction, DeterministicKey priv, NetworkParameters network) {
        List<IndexedTransactionSignature> result = new ArrayList<>();
        for (int i = 0; i < transaction.getInputs().size(); i++) {
            TransactionOutput connectedOutput = transaction
                    .getInput(i)
                    .getOutpoint()
                    .getConnectedOutput();

            if (Arrays.equals(
                    connectedOutput
                            .getAddressFromP2PKHScript(network)
                            .getHash160(),
                    priv.getPubKeyHash())) {

                result.add(
                        new IndexedTransactionSignature(
                                transaction.calculateSignature(
                                        i,
                                        priv,
                                        connectedOutput.getScriptBytes(),
                                        Transaction.SigHash.ALL,
                                        false),
                                i));
            }
        }
        return result;
    }

    private static class IndexedTransactionSignature {
        private final TransactionSignature signature;
        private final int index;

        private IndexedTransactionSignature(TransactionSignature signature, int index) {
            this.signature = signature;
            this.index = index;
        }
    }
}
