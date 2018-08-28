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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet2Params;
import org.bitcoinj.params.UnitTestParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

import static org.openkuva.kuvabase.bwcj.domain.utils.ListUtils.split;
import static org.bitcoinj.core.Utils.HEX;

public class CreateWalletTest {
    private final       String PATHS_REQUEST_KEY               = "m/1'/0";
    public static final String DEFAULT_PASSPHRASE_FOR_MNEMONIC = "";


    private static final byte[] TEST_KEY_PRIV = HEX.decode("6421e091445ade4b24658e96aa60959ce800d8ea9e7bd8613335aa65ba8d840b");
    public static final  String privKey       = "a7bcae46a013ae011dc3ffeeb78997ab89d53ff78347f1b82024090db842d5fa";
    public static final  String message       = "post|/v2/wallets/|{\"name\":\"{\\\"iv\\\":\\\"7QYPS+aObA6EH0lBotsArg==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"NGHVfT0JTQA8fWoJne1alRLPOB/nkgY=\\\"}\",\"m\":1,\"n\":1,\"pubKey\":\"02e009cb18b60819a3fdef5029c65ae365da0b2ca2385c610209f509c713e6ae65\",\"network\":\"livenet\",\"singleAddress\":true}";
    private org.bitcoinj.core.NetworkParameters params;
    private static final String WORDS = "path wrist announce gallery laptop suit aim occur car bid master entire";


    @Before
    public void setUp() throws Exception {
        final ECKey key = ECKey.fromPrivate(TEST_KEY_PRIV);
        params = new UnitTestParams() {
            @Override
            public byte[] getAlertSigningKey() {
                return key.getPubKey();
            }
        };
    }

    @Test
    public void xprivkey() {
        String serializePrivB58 =
                HDKeyDerivation.createMasterPrivateKey(
                        MnemonicCode.toSeed(
                                split(WORDS),
                                ""))
                        .serializePrivB58(params);
        System.out.println(serializePrivB58);
    }

    @Test
    public void x_signature() throws Exception {
        Assert.assertEquals(
                "3045022100e9733636f236423ea23c9ef67e019120abaebfca5d2923931c1dd5a122e508f2022044d7ff181fd94f5422b5a52cb50f7fe09cb89ab04cbfc2a7c3baefb136c72a95",
                signMessage(message, privKey)
        );
    }

    private String signMessage(String message, String privKey) {
        return
                Utils.HEX.encode(
                        ECKey.fromPrivate(
                                Utils.HEX.decode(privKey))
                                .sign(
                                        Sha256Hash.wrap(
                                                Sha256Hash.hashTwice(
                                                        message.getBytes())))
                                .encodeToDER());
    }

    @Test
    public void copayer_id() throws Exception {
        Assert.assertEquals(
                "8728130ca6db89d3a5f7825669d63efc3f1a744138dcf9f77fe9ac6ab9cb49cb",
                getSha256Hex("xpub6C4hbaNitRinDanyZVL56ZHGr9FHgVUXZw1hYSrxhXqas9es22PEZ527FXmuu4UV3ZG6TZgCRs7bDANZRKjDgpRsmXP5a44oGwK5tjtrFGF").toLowerCase());
    }

    public static String getSha256Hex(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String shaHex = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(text.getBytes());
        byte[] digest = md.digest();

        shaHex = DatatypeConverter.printHexBinary(digest);

        return shaHex;
    }

    @Test
    public void createMnemonic() throws Exception {
        new DeterministicSeed(new SecureRandom(), DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS, DEFAULT_PASSPHRASE_FOR_MNEMONIC, Utils.currentTimeSeconds());
    }

    @Test
    public void derivedXPrivKey() throws Exception {

        DeterministicKey derivedXPrivKey =
                HDKeyDerivation.createMasterPrivateKey(
                        MnemonicCode.toSeed(
                                split("bind cattle drop vendor kiss mass good throw dizzy calm crawl raise"),
                                ""))
                        .derive(44)
                        .derive(1)
                        .derive(0);

        Assert.assertEquals(
                "tpubDCEXJGk7oi45Gzp1XJziZj4mLQREsLP8epGELRyYjJZGQSAo9bY6aMW8NT82hqFjMVYSr7WUBjPTSqa1ZqaStt7a8yFmG6Z47TxPrBGi7ar",
                derivedXPrivKey.serializePubB58(TestNet2Params.get())
        );
    }

    @Test
    public void xPubKey() throws Exception {
        derivedXPrivKey();
    }

    @Test
    public void requestDerivation() throws Exception {
        DeterministicKey requestDerivation =
                HDKeyDerivation.deriveChildKey(
                        HDKeyDerivation.createMasterPrivateKey(
                                MnemonicCode.toSeed(
                                        split("path wrist announce gallery laptop suit aim occur car bid master entire"),
                                        ""))
                                .derive(1),
                        new ChildNumber(0, false));
        Assert.assertEquals(
                "xprv9wYVLcWe6Q8qSPkYs3gX5QGxQDp2xG6KJzPW54499Diijcf17D82YWUX35f8L2AumTokpo4h5cAQBXBaAfrijkyC87kq9RNm5eFSMyyhq5h",
                requestDerivation.serializePrivB58(MainNetParams.get()));

        //requestPrivKey
        Assert.assertEquals(
                "03003d91e8445fe33aabcaa3f5076b3ddbecb43592a1311a61e2e40411dce5ec",
                requestDerivation.getPrivateKeyAsHex());

        //requestPubKey
        Assert.assertEquals(
                "02fc41cac227bc60bc475b9df63c6bacd4cb60ffd0197596ed1d2b8c3b24a8bd8f",
                requestDerivation.getPublicKeyAsHex());
    }

    @Test
    public void requestPrivKey() throws Exception {
        requestDerivation();
    }

    @Test
    public void requestPubKey() throws Exception {
        requestDerivation();
    }

    @Test
    public void sharedEncryptingKey() throws Exception {
        byte[] hash =
                Sha256Hash.hash(HEX.decode("215c22405a702de9664b49c22fa156d1e26f1c29a78897deb72bbeef0b03c9d9"));
        byte[] bytes = new byte[16];
        System.arraycopy(hash, 0, bytes, 0, bytes.length);
        String actual = Base64.encode(bytes);
        Assert.assertEquals("xDw+sZ+M1e9UXNFYz7sY3g==", actual);
    }

    @Test
    public void fullTest() throws Exception {
        DeterministicSeed deterministicSeed =
                new DeterministicSeed(
                        new SecureRandom(),
                        DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS,
                        DEFAULT_PASSPHRASE_FOR_MNEMONIC,
                        Utils.currentTimeSeconds());

        DeterministicKey masterPrivateKey =
                HDKeyDerivation.createMasterPrivateKey(
                        deterministicSeed.getSeedBytes());

        String pubKey = "03f3d227222db2f270fd5fa6104f0ed253df8689a81d46734de6892ad7f1ba9b73";
        String name = "{\"iv\":\"TNnAKj3CglbVcvJbEyiC3g==\",\"v\":1,\"iter\":1,\"ks\":128,\"ts\":64,\"mode\":\"ccm\",\"adata\":\"\",\"cipher\":\"aes\",\"ct\":\"sLbGIJzfk/95FQc1Mz6VixFq5J0pHzU=\"}";
        int m = 1;
        int n = 1;
        String network = "testnet";
        boolean singleAddress = true;

        String x_identity =
                getSha256Hex(
                        masterPrivateKey.serializePubB58(
                                TestNet2Params.get()));

        String message = "post|/v2/wallets/|{\"name\":\"{\\\"iv\\\":\\\"TNnAKj3CglbVcvJbEyiC3g==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"sLbGIJzfk/95FQc1Mz6VixFq5J0pHzU=\\\"}\",\"m\":1,\"n\":1,\"pubKey\":\"03f3d227222db2f270fd5fa6104f0ed253df8689a81d46734de6892ad7f1ba9b73\",\"network\":\"testnet\",\"singleAddress\":true}";
        String x_signature =
                signMessage(
                        message,
                        HDKeyDerivation.deriveChildKey(
                                masterPrivateKey,
                                new ChildNumber(0, false))
                                .getPrivateKeyAsHex());
    }


}
