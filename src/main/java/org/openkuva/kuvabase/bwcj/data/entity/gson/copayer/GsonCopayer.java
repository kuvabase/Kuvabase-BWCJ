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

package org.openkuva.kuvabase.bwcj.data.entity.gson.copayer;

import com.google.gson.annotations.SerializedName;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.copayer.ICopayer;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.copayer.IRequestPubKey;

public class GsonCopayer implements ICopayer {
    @SerializedName("coin")
    private String coin;
    @SerializedName("createdOn")
    private long createdOn;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("requestPubKeys")
    private GsonRequestPubKey[] requestPubKeys;
    @SerializedName("version")
    private long version;
    @SerializedName("xPubKey")
    private String xPublicKey;
    @SerializedName("requestPubKey")
    private String requestPublicKey;
    @SerializedName("signature")
    private String signature;
    @SerializedName("customData")
    private String customData;

    public GsonCopayer() {
    }

    public GsonCopayer(ICopayer origin) {
        this.coin = origin.getCoin();
        this.createdOn = origin.getCreatedOn();
        this.id = origin.getId();
        this.name = origin.getName();
        this.requestPubKeys = mapRequestPubKeys(origin.getRequestPubKeys());
        this.version = origin.getVersion();
    }

    private static GsonRequestPubKey[] mapRequestPubKeys(IRequestPubKey[] origin) {
        if (origin == null) {
            return null;
        }

        GsonRequestPubKey[] result = new GsonRequestPubKey[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonRequestPubKey(origin[i]);
        }
        return result;
    }

    @Override
    public String getCoin() {
        return coin;
    }

    @Override
    public long getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GsonRequestPubKey[] getRequestPubKeys() {
        return requestPubKeys;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public String getxPublicKey() {
        return xPublicKey;
    }

    @Override
    public String getRequestPublicKey() {
        return requestPublicKey;
    }

    @Override
    public String getSignature() {
        return signature;
    }

    @Override
    public String getCustomData() {
        return customData;
    }
}
