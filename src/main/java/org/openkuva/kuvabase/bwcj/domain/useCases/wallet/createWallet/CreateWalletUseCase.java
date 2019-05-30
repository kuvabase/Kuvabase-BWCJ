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

package org.openkuva.kuvabase.bwcj.domain.useCases.wallet.createWallet;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;
import org.openkuva.kuvabase.bwcj.domain.utils.CopayersCryptUtils;
import org.openkuva.kuvabase.bwcj.domain.utils.NetworkParametersUtils;
import org.openkuva.kuvabase.bwcj.domain.utils.messageEncrypt.SjclMessageEncryptor;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.IBitcoreWalletServerAPI;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.pojo.wallets.CreateWalletRequest;

import static org.openkuva.kuvabase.bwcj.domain.useCases.wallet.DefaultConstants.DEFAULT_COIN;
import static org.openkuva.kuvabase.bwcj.domain.useCases.wallet.DefaultConstants.DEFAULT_M;
import static org.openkuva.kuvabase.bwcj.domain.useCases.wallet.DefaultConstants.DEFAULT_N;
import static org.openkuva.kuvabase.bwcj.domain.useCases.wallet.DefaultConstants.DEFAULT_SINGLE_ADDRESS;
import static org.openkuva.kuvabase.bwcj.domain.useCases.wallet.DefaultConstants.DEFAULT_WALLET_NAME;

public class CreateWalletUseCase implements ICreateWalletUseCase {

    private final ICredentials credentials;
    private final CopayersCryptUtils copayersCryptUtils;
    private final IBitcoreWalletServerAPI bwsApi;

    public CreateWalletUseCase(
            ICredentials credentials,
            CopayersCryptUtils copayersCryptUtils,
            IBitcoreWalletServerAPI bwsApi) {

        this.credentials = credentials;
        this.copayersCryptUtils = copayersCryptUtils;
        this.bwsApi = bwsApi;
    }

    @Override
    public String execute() {
        return execute(DEFAULT_SINGLE_ADDRESS);
    }

    @Override
    public String execute(boolean singleAddress) {
        return execute(singleAddress, DEFAULT_COIN);
    }

    @Override
    public String execute(boolean singleAddress, String coin) {
        return
                bwsApi.postWallets(
                        new CreateWalletRequest(
                                DEFAULT_M,
                                DEFAULT_N,
                                new SjclMessageEncryptor()
                                        .encrypt(
                                                DEFAULT_WALLET_NAME,
                                                copayersCryptUtils.sharedEncryptingKey(
                                                        credentials.getWalletPrivateKey()
                                                                .getPrivateKeyAsHex())),
                                NetworkParametersUtils.fromId(
                                        credentials
                                                .getNetworkParameters()
                                                .getId()),
                                credentials
                                        .getWalletPrivateKey()
                                        .getPublicKeyAsHex(),
                                singleAddress,
                                coin))
                        .getWalletID();

    }
}
