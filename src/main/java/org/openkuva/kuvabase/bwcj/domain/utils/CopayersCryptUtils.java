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

package org.openkuva.kuvabase.bwcj.domain.utils;

import com.google.common.io.BaseEncoding;
import com.google.gson.GsonBuilder;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.bitcoinj.core.Utils.HEX;
import static org.openkuva.kuvabase.bwcj.domain.utils.ListUtils.join;

public final class CopayersCryptUtils {

    private final ICoinTypeRetriever coinTypeRetriever;

    public CopayersCryptUtils(ICoinTypeRetriever coinTypeRetriever) {
        this.coinTypeRetriever = coinTypeRetriever;
    }

    public String copayerId(byte[] seed, NetworkParameters netParams) {
        return
                xPubToCopayerId(
                        xPubKey(
                                derivedXPrivKey(seed, netParams),
                                netParams));
    }

    public DeterministicKey derivedXPrivKey(byte[] seed, NetworkParameters netParams) {
        return
                derivedXPrivKey(
                        HDKeyDerivation.createMasterPrivateKey(seed),
                        netParams);
    }

    public DeterministicKey requestDerivation(byte[] seed) {
        return
                HDKeyDerivation.deriveChildKey(
                        HDKeyDerivation.createMasterPrivateKey(seed)
                                .derive(1),
                        new ChildNumber(0, false));
    }

    public String signMessage(String message, String privKey) {
        return
                Utils.HEX.encode(
                        ECKey.fromPrivate(
                                Utils.HEX.decode(privKey))
                                .sign(Sha256Hash.wrap(
                                        Sha256Hash.hashTwice(
                                                message.getBytes())))
                                .encodeToDER());
    }

    public String entropySource(DeterministicKey requestDerivation) {
        return
                HEX.encode(
                        Sha256Hash.hash(
                                HEX.decode(
                                        requestDerivation.getPrivateKeyAsHex())));
    }

    public String xPubToCopayerId(String xpub) {
        try {
            String shaHex = "";
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(xpub.getBytes());
            byte[] digest = md.digest();

            shaHex = Utils.HEX.encode(digest);
            return shaHex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String personalEncryptingKey(String entropySource) {
        try {
            String prefix = "personalKey";

            byte[] hash =
                    sha256hmac(
                            HEX.decode(entropySource),
                            prefix.getBytes());
            byte[] bytes = new byte[16];
            System.arraycopy(hash, 0, bytes, 0, bytes.length);

            return BaseEncoding.base64().encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] sha256hmac(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha512Hmac = Mac.getInstance("HmacSHA256");

        sha512Hmac.init(
                new SecretKeySpec(
                        key,
                        "HmacSHA256"));
        //result = Base64.encode(macData);
        return
                sha512Hmac.
                        doFinal(data);

    }

    public String getCopayerHash(String encCopayerName, String xPubKey, String requestPubKey) {
        return join(Arrays.asList(encCopayerName, xPubKey, requestPubKey), "|");
    }

    public String signRequest(String method, String url, Object arg, String key) {
        return
                signMessage(
                        join(
                                Arrays.asList(
                                        method,
                                        url,
                                        new GsonBuilder()
                                                .disableHtmlEscaping()
                                                .create()
                                                .toJson(arg)),
                                "|"),
                        key);
    }

    public String sharedEncryptingKey(String walletPrivKey) {
        byte[] hash =
                Sha256Hash.hash(
                        HEX.decode(walletPrivKey));
        byte[] bytes = new byte[16];
        System.arraycopy(hash, 0, bytes, 0, bytes.length);
        return BaseEncoding.base64().encode(bytes);
    }

    public DeterministicKey derivedXPrivKey(DeterministicKey masterPrivateKey, NetworkParameters netParams) {
        return masterPrivateKey
                .derive(44)
                .derive(getCoinFromNetwork(netParams))
                .derive(0);
    }

    private int getCoinFromNetwork(NetworkParameters network) {
        return coinTypeRetriever.fromParams(network);
    }

    public String xPubKey(DeterministicKey derivedXPrivKey, NetworkParameters netParams) {
        return derivedXPrivKey.serializePubB58(netParams);
    }
}
