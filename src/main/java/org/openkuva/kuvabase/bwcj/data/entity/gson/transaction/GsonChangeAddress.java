
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

package org.openkuva.kuvabase.bwcj.data.entity.gson.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.IChangeAddress;

public class GsonChangeAddress implements IChangeAddress {

    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("createdOn")
    @Expose
    public int createdOn;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("walletId")
    @Expose
    public String walletId;
    @SerializedName("coin")
    @Expose
    public String coin;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("isChange")
    @Expose
    public boolean isChange;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("publicKeys")
    @Expose
    public List<String> publicKeys = null;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("hasActivity")
    @Expose
    public Object hasActivity;

    public GsonChangeAddress(IChangeAddress changeAddress) {
        this.version = changeAddress.getVersion();
        this.createdOn = changeAddress.getCreatedOn();
        this.address = changeAddress.getAddress();
        this.walletId = changeAddress.getWalletId();
        this.coin = changeAddress.getCoin();
        this.network = changeAddress.getNetwork();
        this.isChange = changeAddress.isChange();
        this.path = changeAddress.getPath();
        this.publicKeys = changeAddress.getPublicKeys();
        this.type = changeAddress.getType();
        this.hasActivity = changeAddress.getHasActivity();
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public int getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getWalletId() {
        return walletId;
    }

    @Override
    public String getCoin() {
        return coin;
    }

    @Override
    public String getNetwork() {
        return network;
    }

    @Override
    public boolean isChange() {
        return isChange;
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
    public Object getHasActivity() {
        return hasActivity;
    }
}
