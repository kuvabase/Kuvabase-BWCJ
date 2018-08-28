
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

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.IAction;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.IInput;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.IOutput;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionProposal;

public class GsonTransactionProposal implements ITransactionProposal {

    @SerializedName("version")
    @Expose
    public int version;
    @SerializedName("createdOn")
    @Expose
    public int createdOn;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("walletId")
    @Expose
    public String walletId;
    @SerializedName("creatorId")
    @Expose
    public String creatorId;
    @SerializedName("coin")
    @Expose
    public String coin;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("outputs")
    @Expose
    public GsonOutput[] outputs = null;
    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("message")
    @Expose
    public Object message;
    @SerializedName("payProUrl")
    @Expose
    public Object payProUrl;
    @SerializedName("changeAddress")
    @Expose
    public GsonChangeAddress changeAddress;
    @SerializedName("inputs")
    @Expose
    public GsonInput[] inputs = null;
    @SerializedName("walletM")
    @Expose
    public int walletM;
    @SerializedName("walletN")
    @Expose
    public int walletN;
    @SerializedName("requiredSignatures")
    @Expose
    public int requiredSignatures;
    @SerializedName("requiredRejections")
    @Expose
    public int requiredRejections;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("inputPaths")
    @Expose
    public List<String> inputPaths = null;
    @SerializedName("actions")
    @Expose
    public GsonAction[] actions = null;
    @SerializedName("outputOrder")
    @Expose
    public List<Integer> outputOrder = null;
    @SerializedName("fee")
    @Expose
    public int fee;
    @SerializedName("feeLevel")
    @Expose
    public String feeLevel;
    @SerializedName("feePerKb")
    @Expose
    public int feePerKb;
    @SerializedName("excludeUnconfirmedUtxos")
    @Expose
    public boolean excludeUnconfirmedUtxos;
    @SerializedName("addressType")
    @Expose
    public String addressType;
    @SerializedName("customData")
    @Expose
    public GsonCustomData customData;
    @SerializedName("proposalSignature")
    @Expose
    public String proposalSignature;
    @SerializedName("isInstantSend")
    @Expose
    public boolean isInstantSend;
    @SerializedName("derivationStrategy")
    @Expose
    public String derivationStrategy;
    @SerializedName("creatorName")
    @Expose
    public String creatorName;
    @SerializedName("txid")
    @Expose
    private String txid;
    @SerializedName("broadcastedOn")
    @Expose
    public int broadcastedOn;
    @SerializedName("proposalSignaturePubKey")
    @Expose
    public Object proposalSignaturePubKey;
    @SerializedName("proposalSignaturePubKeySig")
    @Expose
    public Object proposalSignaturePubKeySig;
    @SerializedName("raw")
    @Expose
    public String raw;

    public GsonTransactionProposal() {
    }

    public GsonTransactionProposal(ITransactionProposal origin) {
        version = origin.getVersion();
        createdOn = origin.getCreatedOn();
        id = origin.getId();
        walletId = origin.getWalletId();
        creatorId = origin.getCreatorId();
        coin = origin.getCoin();
        network = origin.getNetwork();
        outputs = mapOutputs(origin.getOutputs());
        amount = origin.getAmount();
        message = origin.getMessage();
        payProUrl = origin.getPayProUrl();
        changeAddress =
                origin.getChangeAddress() == null
                        ? null : new GsonChangeAddress(origin.getChangeAddress());
        inputs = mapInputs(origin.getInputs());
        walletM = origin.getWalletM();
        walletN = origin.getWalletN();
        requiredSignatures = origin.getRequiredSignatures();
        requiredRejections = origin.getRequiredRejections();
        status = origin.getStatus();
        inputPaths = origin.getInputPaths();
        actions = mapActions(origin.getActions());
        outputOrder = origin.getOutputOrder();
        fee = origin.getFee();
        feeLevel = origin.getFeeLevel();
        feePerKb = origin.getFeePerKb();
        excludeUnconfirmedUtxos = origin.isExcludeUnconfirmedUtxos();
        addressType = origin.getAddressType();
        customData =
                origin.getCustomData() == null
                        ? null : new GsonCustomData(origin.getCustomData());
        proposalSignature = origin.getProposalSignature();
        isInstantSend = origin.isInstantSend();
        derivationStrategy = origin.getDerivationStrategy();
        creatorName = origin.getCreatorName();
        broadcastedOn = origin.getBroadcastedOn();
        proposalSignaturePubKey = origin.getProposalSignaturePubKey();
        proposalSignaturePubKeySig = origin.getProposalSignaturePubKeySig();
        raw = origin.getRaw();
    }

    private static GsonAction[] mapActions(IAction[] origin) {
        if (origin == null) {
            return null;
        }

        GsonAction[] result = new GsonAction[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonAction(origin[i]);
        }
        return result;
    }

    private static GsonOutput[] mapOutputs(IOutput[] origin) {
        if (origin == null) {
            return null;
        }

        GsonOutput[] result = new GsonOutput[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonOutput(origin[i]);
        }
        return result;
    }

    private static GsonInput[] mapInputs(IInput[] origin) {
        if (origin == null) {
            return null;
        }

        GsonInput[] result = new GsonInput[origin.length];
        for (int i = 0; i < origin.length; i++) {
            result[i] = new GsonInput(origin[i]);
        }
        return result;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public int getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getWalletId() {
        return walletId;
    }

    @Override
    public String getCreatorId() {
        return creatorId;
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
    public GsonOutput[] getOutputs() {
        return outputs;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public Object getPayProUrl() {
        return payProUrl;
    }

    @Override
    public GsonChangeAddress getChangeAddress() {
        return changeAddress;
    }

    @Override
    public GsonInput[] getInputs() {
        return inputs;
    }

    @Override
    public int getWalletM() {
        return walletM;
    }

    @Override
    public int getWalletN() {
        return walletN;
    }

    @Override
    public int getRequiredSignatures() {
        return requiredSignatures;
    }

    @Override
    public int getRequiredRejections() {
        return requiredRejections;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getTxid() {
        return txid;
    }

    @Override
    public int getBroadcastedOn() {
        return broadcastedOn;
    }

    @Override
    public List<String> getInputPaths() {
        return inputPaths;
    }

    @Override
    public GsonAction[] getActions() {
        return actions;
    }

    @Override
    public List<Integer> getOutputOrder() {
        return outputOrder;
    }

    @Override
    public int getFee() {
        return fee;
    }

    @Override
    public String getFeeLevel() {
        return feeLevel;
    }

    @Override
    public int getFeePerKb() {
        return feePerKb;
    }

    @Override
    public boolean isExcludeUnconfirmedUtxos() {
        return excludeUnconfirmedUtxos;
    }

    @Override
    public String getAddressType() {
        return addressType;
    }

    @Override
    public GsonCustomData getCustomData() {
        return customData;
    }

    public String getProposalSignature() {
        return proposalSignature;
    }

    @Override
    public Object getProposalSignaturePubKey() {
        return proposalSignaturePubKey;
    }

    @Override
    public Object getProposalSignaturePubKeySig() {
        return proposalSignaturePubKeySig;
    }

    @Override
    public boolean isInstantSend() {
        return isInstantSend;
    }

    @Override
    public String getDerivationStrategy() {
        return derivationStrategy;
    }

    @Override
    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
