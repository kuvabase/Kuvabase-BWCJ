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

package org.openkuva.kuvabase.bwcj.data.entity.gson.wallet;

import com.google.gson.annotations.SerializedName;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.copayer.ICopayer;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.copayer.IPublicKeyRing;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.wallet.IWalletCore;
import org.openkuva.kuvabase.bwcj.data.entity.gson.copayer.GsonAddressManager;
import org.openkuva.kuvabase.bwcj.data.entity.gson.copayer.GsonCopayer;
import org.openkuva.kuvabase.bwcj.data.entity.gson.copayer.GsonPublicKeyRing;

public class GsonWalletCore implements IWalletCore {
    @SerializedName("addressType")
    private String addressType;
    @SerializedName("coin")
    private String coin;
    @SerializedName("copayers")
    private GsonCopayer[] copayers;
    @SerializedName("createdOn")
    private long createdOn;
    @SerializedName("derivationStrategy")
    private String derivationStrategy;
    @SerializedName("id")
    private String id;
    @SerializedName("m")
    private long m;
    @SerializedName("n")
    private long n;
    @SerializedName("name")
    private String name;
    @SerializedName("network")
    private String network;
    @SerializedName("scanStatus")
    private String scanStatus;
    @SerializedName("singleAddress")
    private boolean singleAddress;
    @SerializedName("status")
    private String status;
    @SerializedName("version")
    private String version;
    @SerializedName("addressManager")
    private GsonAddressManager addressManager;
    @SerializedName("pubKey")
    private String publickKey;
    @SerializedName("publicKeyRing")
    private GsonPublicKeyRing[] publicKeyRings;

    public GsonWalletCore() {
    }

    public GsonWalletCore(IWalletCore origin) {
        this.addressType = origin.getAddressType();
        this.coin = origin.getCoin();
        this.copayers = mapCopayers(origin.getCopayers());
        this.createdOn = origin.getCreatedOn();
        this.derivationStrategy = origin.getDerivationStrategy();
        this.id = origin.getId();
        this.m = origin.getM();
        this.n = origin.getN();
        this.name = origin.getName();
        this.network = origin.getNetwork();
        this.scanStatus = origin.getScanStatus();
        this.singleAddress = origin.getSingleAddress();
        this.status = origin.getStatus();
        this.version = origin.getVersion();
        this.addressManager =
                origin.getAddressManager() == null
                        ? null : new GsonAddressManager(origin.getAddressManager());
        this.publickKey = origin.getPublickKey();
        this.publicKeyRings = mapPublicKeyRings(origin.getPublicKeyRings());
    }

    private static GsonCopayer[] mapCopayers(ICopayer[] origin) {
        if (origin == null) {
            return null;
        }

        GsonCopayer[] result = new GsonCopayer[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonCopayer(origin[i]);
        }
        return result;
    }

    private static GsonPublicKeyRing[] mapPublicKeyRings(IPublicKeyRing[] origin) {
        if (origin == null) {
            return null;
        }

        GsonPublicKeyRing[] result = new GsonPublicKeyRing[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonPublicKeyRing(origin[i]);
        }
        return result;
    }

    @Override
    public String getAddressType() {
        return addressType;
    }

    @Override
    public String getCoin() {
        return coin;
    }

    @Override
    public GsonCopayer[] getCopayers() {
        return copayers;
    }

    @Override
    public long getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getDerivationStrategy() {
        return derivationStrategy;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getM() {
        return m;
    }

    @Override
    public long getN() {
        return n;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNetwork() {
        return network;
    }

    @Override
    public String getScanStatus() {
        return scanStatus;
    }

    @Override
    public boolean getSingleAddress() {
        return singleAddress;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public IPublicKeyRing[] getPublicKeyRings() {
        return publicKeyRings;
    }

    @Override
    public String getPublickKey() {
        return publickKey;
    }

    @Override
    public GsonAddressManager getAddressManager() {
        return addressManager;
    }
}