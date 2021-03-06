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

package org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.pojo.wallets;

import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletRequest;

public class JoinWalletRequest implements IJoinWalletRequest {
    private final String walletId;
    private final String name;
    private final String xPubKey;
    private final String requestPubKey;
    private final String customData;
    private final String copayerSignature;
    private final String coin;

    public JoinWalletRequest(String copayerSignature, String customData, String name, String requestPubKey, String walletId, String xPubKey, String coin) {
        this.copayerSignature = copayerSignature;
        this.customData = customData;
        this.name = name;
        this.requestPubKey = requestPubKey;
        this.walletId = walletId;
        this.xPubKey = xPubKey;
        this.coin = coin;
    }

    @Override
    public String getCopayerSignature() {
        return copayerSignature;
    }

    @Override
    public String getCustomData() {
        return customData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRequestPubKey() {
        return requestPubKey;
    }

    @Override
    public String getWalletId() {
        return walletId;
    }

    @Override
    public String getxPubKey() {
        return xPubKey;
    }

    @Override
    public String getCoin() {
        return coin;
    }
}
