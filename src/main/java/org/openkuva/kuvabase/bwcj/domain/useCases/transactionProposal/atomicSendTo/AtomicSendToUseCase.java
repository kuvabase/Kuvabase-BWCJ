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

package org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.atomicSendTo;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ICustomData;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionProposal;
import org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.addNewTxp.IAddNewTxpUseCase;
import org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.broadcastTxp.IBroadcastTxpUseCase;
import org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.deletePendingTxp.IDeletePendingTxpUseCase;
import org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.publishTxp.IPublishTxpUseCase;
import org.openkuva.kuvabase.bwcj.domain.useCases.transactionProposal.signTxp.ISignTxpUseCase;

public class AtomicSendToUseCase implements IAtomicSendToUseCase {
    private final IAddNewTxpUseCase addNewTxpUseCases;
    private final IPublishTxpUseCase publishTxpUseCases;
    private final ISignTxpUseCase signTxpUseCases;
    private final IBroadcastTxpUseCase broadcastTxpUseCases;
    private final IDeletePendingTxpUseCase deletePendingTxp;

    public AtomicSendToUseCase(
            IAddNewTxpUseCase addNewTxpUseCases,
            IPublishTxpUseCase publishTxpUseCases,
            ISignTxpUseCase signTxpUseCases,
            IBroadcastTxpUseCase broadcastTxpUseCases,
            IDeletePendingTxpUseCase deletePendingTxp) {

        this.addNewTxpUseCases = addNewTxpUseCases;
        this.publishTxpUseCases = publishTxpUseCases;
        this.signTxpUseCases = signTxpUseCases;
        this.broadcastTxpUseCases = broadcastTxpUseCases;
        this.deletePendingTxp = deletePendingTxp;
    }

    @Override
    public ITransactionProposal execute(String address, String dash, String msg, ICustomData customData) {
        return execute(address, dash, msg, "send", customData);
    }

    @Override
    public ITransactionProposal execute(String address, String dash, String msg, String operation, ICustomData customData) {
        String txId = null;
        try {
            ITransactionProposal txToPublish =
                    addNewTxpUseCases.execute(
                            address,
                            dash,
                            msg,
                            false,
                            operation,
                            customData);
            txId = txToPublish.getId();

            ITransactionProposal txToSign =
                    publishTxpUseCases.execute(txToPublish);
            txId = txToSign.getId();

            ITransactionProposal signedTx =
                    signTxpUseCases.execute(txToSign);
            txId = signedTx.getId();

            return broadcastTxpUseCases.execute(signedTx.getId());
        } catch (Exception e) {
            if (txId != null) {
                deletePendingTxp.execute(txId);
            }
            throw e;
        }
    }
}
