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

package org.openkuva.kuvabase.bwcj.domain.useCases.wallet.joinWalletInCreation;

import com.google.gson.GsonBuilder;

import org.bitcoinj.core.ECKey;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;
import org.openkuva.kuvabase.bwcj.domain.utils.CopayersCryptUtils;
import org.openkuva.kuvabase.bwcj.domain.utils.messageEncrypt.SjclMessageEncryptor;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.IBitcoreWalletServerAPI;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.pojo.wallets.JoinWalletRequest;

public class JoinWalletInCreationUseCase implements IJoinWalletInCreationUseCase {

    private final ICredentials credentials;
    private final IBitcoreWalletServerAPI bwsApi;

    public JoinWalletInCreationUseCase(ICredentials credentials, IBitcoreWalletServerAPI bwsApi) {
        this.credentials = credentials;
        this.bwsApi = bwsApi;
    }

    @Override
    public IJoinWalletResponse execute(String walletId) {
        ECKey walletPrivKey =
                credentials.getWalletPrivateKey();
        String xPubKey =
                CopayersCryptUtils.derivedXPrivKey(
                        credentials.getSeed(),
                        credentials.getNetworkParameters())
                        .serializePubB58(credentials.getNetworkParameters());

        String requestPubKey =
                CopayersCryptUtils.requestDerivation(
                        credentials.getSeed())
                        .getPublicKeyAsHex();

        String personalEncryptingKey =
                CopayersCryptUtils.personalEncryptingKey(
                        CopayersCryptUtils.entropySource(
                                CopayersCryptUtils.requestDerivation(
                                        credentials.getSeed())));

        String encCustomData =
                new SjclMessageEncryptor()
                        .encrypt(
                                new GsonBuilder()
                                        .disableHtmlEscaping()
                                        .create()
                                        .toJson(
                                                new CustomData(
                                                        walletPrivKey.getPrivateKeyAsHex())),
                                personalEncryptingKey);

        String encCopayerName =
                new SjclMessageEncryptor()
                        .encrypt(
                                "me",
                                CopayersCryptUtils.sharedEncryptingKey(
                                        walletPrivKey.getPrivateKeyAsHex()));

        String hash =
                CopayersCryptUtils.getCopayerHash(
                        encCopayerName,
                        xPubKey,
                        requestPubKey);

        String copayerSignature = CopayersCryptUtils.signMessage(hash, walletPrivKey.getPrivateKeyAsHex());

        IJoinWalletRequest joinWalletRequest =
                new JoinWalletRequest(
                        copayerSignature,
                        encCustomData,
                        encCopayerName,
                        requestPubKey,
                        walletId,
                        xPubKey);

        return
                bwsApi.postWalletsWalletIdCopayers(
                        walletId,
                        joinWalletRequest);
    }

}
