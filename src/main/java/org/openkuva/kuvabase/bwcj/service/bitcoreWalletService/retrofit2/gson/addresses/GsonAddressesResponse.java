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

package org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.addresses;

import com.google.gson.annotations.SerializedName;

import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.address.IAddressesResponse;

import java.util.List;

public class GsonAddressesResponse implements IAddressesResponse {
    @SerializedName("address")
    String address;

    @SerializedName("coin")
    String coin;

    @SerializedName("createdOn")
    String createdOn;

    @SerializedName("isChange")
    boolean isChange;

    @SerializedName("network")
    String network;

    @SerializedName("path")
    String path;

    @SerializedName("publicKeys")
    List<String> publicKeys;

    @SerializedName("type")
    String type;

    @SerializedName("version")
    String version;

    @SerializedName("walletId")
    String walletId;

    @SerializedName("_id")
    String _id;

    @SerializedName("hasActivity")
    boolean hasActivity;

    public GsonAddressesResponse(String address,
                                 String coin,
                                 String createdOn,
                                 boolean isChange,
                                 String network,
                                 String path,
                                 List<String> publicKeys,
                                 String type,
                                 String version,
                                 String walletId,
                                 String _id,
                                 boolean hasActivity) {

        this.address = address;
        this.coin = coin;
        this.createdOn = createdOn;
        this.isChange = isChange;
        this.network = network;
        this.path = path;
        this.publicKeys = publicKeys;
        this.type = type;
        this.version = version;
        this.walletId = walletId;
        this._id = _id;
        this.hasActivity = hasActivity;
    }

    @Override
    public boolean hasActivity() {
        return hasActivity;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCoin() {
        return coin;
    }

    @Override
    public String getCreatedOn() {
        return createdOn;
    }

    @Override
    public boolean isChange() {
        return isChange;
    }

    @Override
    public String getNetwork() {
        return network;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<String> getPublicKeys() {
        return publicKeys;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getWalletId() {
        return walletId;
    }

    @Override
    public String get_id() {
        return _id;
    }
}
