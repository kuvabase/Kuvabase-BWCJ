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

import com.google.gson.Gson;

import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.openkuva.kuvabase.bwcj.data.entity.gson.transaction.GsonTransactionProposal;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;
import org.openkuva.kuvabase.bwcj.domain.utils.CommonNetworkParametersBuilder;
import org.openkuva.kuvabase.bwcj.domain.utils.transactions.TransactionBuilder;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.IBitcoreWalletServerAPI;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.signatures.ISignatureRequest;

import static org.openkuva.kuvabase.bwcj.domain.utils.ListUtils.split;
import static org.mockito.Matchers.eq;

public class SignTxpUseCaseTest {

    private final String txp = "{\"version\":3,\"createdOn\":1531750342,\"id\":\"fe7fd95b-85b9-4cf0-bbe2-a26e933ac614\",\"walletId\":\"e9a7e480-d102-465d-adb8-b0e1680e4276\",\"creatorId\":\"f4bf0602e5163fa89cac9ae175354c36d7b23363d5cf3506c155c8e8e9505261\",\"coin\":\"btc\",\"network\":\"testnet\",\"outputs\":[{\"amount\":418286,\"toAddress\":\"yeLocpvyHpUxRpx78QhgBvTm8QSTmbXVxU\",\"message\":null}],\"amount\":418286,\"message\":null,\"payProUrl\":null,\"changeAddress\":{\"version\":\"1.0.0\",\"createdOn\":1528282951,\"address\":\"yT9Fec2K9wdAg2o8MyfTBTLix9JYU5ozbD\",\"walletId\":\"e9a7e480-d102-465d-adb8-b0e1680e4276\",\"coin\":\"btc\",\"network\":\"testnet\",\"isChange\":false,\"path\":\"m/0/0\",\"publicKeys\":[\"02014f08cea37259eb0a503eb2ebe30c7efa06a882858460c99a4e66ff0542727a\"],\"type\":\"P2PKH\",\"hasActivity\":null},\"inputs\":[{\"txid\":\"0db79411cdbe9a12a35148c164197c8abd6d0338fe8c18dc365428aa35a3ca36\",\"vout\":0,\"address\":\"yT9Fec2K9wdAg2o8MyfTBTLix9JYU5ozbD\",\"scriptPubKey\":\"76a9144adb5f21558a7879a4995c67294cecacb4ee90c988ac\",\"satoshis\":435140,\"confirmations\":172,\"locked\":false,\"path\":\"m/0/0\",\"publicKeys\":[\"02014f08cea37259eb0a503eb2ebe30c7efa06a882858460c99a4e66ff0542727a\"]},{\"txid\":\"ca116536667486b1e3f8719f604eaaf56fe2bff0d2129f1293cacea0b8ef78d7\",\"vout\":0,\"address\":\"yT9Fec2K9wdAg2o8MyfTBTLix9JYU5ozbD\",\"scriptPubKey\":\"76a9144adb5f21558a7879a4995c67294cecacb4ee90c988ac\",\"satoshis\":435140,\"confirmations\":171,\"locked\":false,\"path\":\"m/0/0\",\"publicKeys\":[\"02014f08cea37259eb0a503eb2ebe30c7efa06a882858460c99a4e66ff0542727a\"]}],\"walletM\":1,\"walletN\":1,\"requiredSignatures\":1,\"requiredRejections\":1,\"status\":\"pending\",\"inputPaths\":[\"m/0/0\",\"m/0/0\"],\"actions\":[],\"outputOrder\":[0,1],\"fee\":31200,\"feeLevel\":\"normal\",\"feePerKb\":80000,\"excludeUnconfirmedUtxos\":false,\"addressType\":\"P2PKH\",\"customData\":{\"rate\":239.07048556,\"operation\":\"send\",\"bookCashId\":null,\"message\":null},\"proposalSignature\":\"3045022100ad27cf434c4c2820c06c30bb8baf6e457447c00dbdb772ffe26e78978123fa77022034d305ba8681291dfffc5db5da4be832f64763c72625cdcab8c54b51da6404ab\",\"isInstantSend\":false,\"derivationStrategy\":\"BIP44\",\"creatorName\":\"{\\\"iv\\\":\\\"K4j3fstm71cjD07byGss8g==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"dz3kSTVOjfSeMIETSnU=\\\"}\"}";
    private final String[] signatures = new String[]{
            "304402203c5c0d316ed3ce458db28fa6764b0e8fc5e8a58b790ecfe77b56e01414f8a6fb0220420c52ece35dc3cecb0036c7c4dd3cf35772032cd064688c9db811c6ae3af8ec",
            "3045022100eea63774af057d5ba3e8c7208d17baae2b19e187682ea486203f281efad1edbd02202445974dde6442dcb2738700120d2ede51d1f66be302f91e0ba9d48e410cf70d"};

    @Mock
    private IBitcoreWalletServerAPI bwsApi;
    @Mock
    private ICredentials credentials;

    private ISignTxpUseCase signTxpUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(credentials.getSeed()).thenReturn(MnemonicCode.toSeed(split("faith space neutral exhaust worth amused average skull differ track best obey"), ""));
        Mockito.when(credentials.getNetworkParameters()).thenReturn(TestNet3Params.get());

        signTxpUseCase = new SignTxpUseCase(bwsApi, credentials, new TransactionBuilder(new CommonNetworkParametersBuilder()));
    }

    @Test
    public void testExecute() {
        signTxpUseCase.execute(
                new Gson().fromJson(
                        txp,
                        GsonTransactionProposal.class));

        ArgumentCaptor<ISignatureRequest> captor = ArgumentCaptor.forClass(ISignatureRequest.class);
        Mockito.verify(bwsApi)
                .postTxProposalsTxIdSignatures(
                        eq("fe7fd95b-85b9-4cf0-bbe2-a26e933ac614"),
                        captor.capture());

        Assert.assertArrayEquals(
                signatures,
                captor
                        .getValue()
                        .getSignatures()
                        .toArray());
    }
}