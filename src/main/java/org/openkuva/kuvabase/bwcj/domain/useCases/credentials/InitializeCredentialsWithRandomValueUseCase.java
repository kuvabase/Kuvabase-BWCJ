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

package org.openkuva.kuvabase.bwcj.domain.useCases.credentials;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.wallet.DeterministicSeed;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

public class InitializeCredentialsWithRandomValueUseCase implements IInitializeCredentialsUseCase {

    private final ICredentials credentials;

    public InitializeCredentialsWithRandomValueUseCase(ICredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public List<String> execute(String passphrase) {
        DeterministicSeed deterministicSeed =
                new DeterministicSeed(
                        new SecureRandom(),
                        DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS,
                        passphrase,
                        Utils.currentTimeSeconds());

        credentials.setSeed(deterministicSeed.getSeedBytes());
        credentials.setWalletPrivateKey(new ECKey());

        return Collections.unmodifiableList(deterministicSeed.getMnemonicCode());
    }

    @Override
    public void execute(List<String> mnemonic, String passphrase) {
        DeterministicSeed deterministicSeed =
                new DeterministicSeed(
                        mnemonic,
                        null,
                        passphrase,
                        Utils.currentTimeSeconds());

        credentials.setSeed(deterministicSeed.getSeedBytes());
        credentials.setWalletPrivateKey(new ECKey());
    }
}
