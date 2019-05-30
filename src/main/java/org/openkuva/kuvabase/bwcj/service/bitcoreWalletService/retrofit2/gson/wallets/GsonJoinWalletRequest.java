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

package org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets;

import com.google.gson.annotations.SerializedName;

import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletRequest;

public class GsonJoinWalletRequest implements IJoinWalletRequest {

    @SerializedName("walletId")
    String walletId;

    @SerializedName("name")
    String name;

    @SerializedName("xPubKey")
    String xPubKey;

    @SerializedName("requestPubKey")
    String requestPubKey;

    @SerializedName("customData")
    String customData;

    @SerializedName("copayerSignature")
    String copayerSignature;

    @SerializedName("coin")
    private String coin;

    public GsonJoinWalletRequest() {
    }

    public GsonJoinWalletRequest(IJoinWalletRequest origin) {
        this.copayerSignature = origin.getCopayerSignature();
        this.customData = origin.getCustomData();
        this.name = origin.getName();
        this.requestPubKey = origin.getRequestPubKey();
        this.walletId = origin.getWalletId();
        this.xPubKey = origin.getxPubKey();
        this.coin = origin.getCoin();
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

