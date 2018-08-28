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

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.openkuva.kuvabase.bwcj.domain.utils.DeriveUtils.deriveChildByPath;
import static org.openkuva.kuvabase.bwcj.domain.utils.ListUtils.split;

public class DeriveUtilsTest {

    @Test
    public void testParsePath1() {
        List<ChildNumber> childNumbers = DeriveUtils.parsePath("m/0/0");
        for (ChildNumber childNumber : childNumbers) {
            Assert.assertEquals(new ChildNumber(0), childNumber);
        }
    }

    @Test
    public void testParsePath2() {
        List<ChildNumber> childNumbers = DeriveUtils.parsePath("m/1'/0");
        Assert.assertEquals(new ChildNumber(1, true), childNumbers.get(0));
        Assert.assertEquals(new ChildNumber(0), childNumbers.get(1));
    }

    @Test
    public void testDeriveChildByPath() {
        DeterministicKey key =
                HDKeyDerivation.createMasterPrivateKey(
                        MnemonicCode.toSeed(
                                split("wheel front quality surround pool crawl scatter wasp cheap process tip ripple"),
                                ""));

        DeterministicKey deriveChildByPath = deriveChildByPath(key, "m/1'");
        Assert.assertEquals(1, deriveChildByPath.getDepth());
        Assert.assertEquals("M/1H", deriveChildByPath.getPathAsString());
    }

    @Test
    public void testDeriveChildByPath1() {
        DeterministicKey key =
                HDKeyDerivation.createMasterPrivateKey(
                        MnemonicCode.toSeed(
                                split("wheel front quality surround pool crawl scatter wasp cheap process tip ripple"),
                                ""));

        DeterministicKey deriveChildByPath = deriveChildByPath(key, "m/1'/0");
        Assert.assertEquals(2, deriveChildByPath.getDepth());
        Assert.assertEquals("M/1H/0", deriveChildByPath.getPathAsString());
    }
}