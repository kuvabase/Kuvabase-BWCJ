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

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionProposal;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.wallet.IWallet;
import org.openkuva.kuvabase.bwcj.data.entity.gson.transaction.GsonTransactionProposal;

public class GsonWallet implements IWallet {
    @SerializedName("wallet")
    private GsonWalletCore walletCore;

    @SerializedName("preferences")
    private GsonPreferences preferences;

    @SerializedName("pendingTxps")
    private GsonTransactionProposal[] pendingTxps;

    @SerializedName("balance")
    private GsonBalance balance;

    public GsonWallet() {
    }

    public GsonWallet(IWallet origin) {
        this.walletCore = new GsonWalletCore(origin.getWalletCore());
        this.preferences = new GsonPreferences(origin.getPreferences());
        this.pendingTxps = mapPendingTxps(origin.getPendingTxps());
        this.balance = new GsonBalance(origin.getBalance());
    }

    private static GsonTransactionProposal[] mapPendingTxps(ITransactionProposal[] origin) {
        if (origin == null) {
            return null;
        }

        GsonTransactionProposal[] result = new GsonTransactionProposal[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonTransactionProposal(origin[i]);
        }
        return result;
    }

    @Override
    public GsonWalletCore getWalletCore() {
        return walletCore;
    }

    @Override
    public GsonPreferences getPreferences() {
        return preferences;
    }

    @Override
    public GsonTransactionProposal[] getPendingTxps() {
        return pendingTxps;
    }

    @Override
    public GsonBalance getBalance() {
        return balance;
    }
}
