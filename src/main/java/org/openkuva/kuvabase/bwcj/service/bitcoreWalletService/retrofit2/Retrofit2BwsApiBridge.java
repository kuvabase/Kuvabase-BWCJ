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

package org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2;

import org.openkuva.kuvabase.bwcj.data.entity.gson.fee.GsonFeeLevel;
import org.openkuva.kuvabase.bwcj.data.entity.gson.transaction.GsonTransactionProposal;
import org.openkuva.kuvabase.bwcj.data.entity.gson.wallet.GsonWallet;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionProposal;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.transaction.ITransactionRequest;
import org.openkuva.kuvabase.bwcj.data.entity.interfaces.wallet.IWallet;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.IBitcoreWalletServerAPI;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.address.AddressesRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.address.IAddressesResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.broadcast.BroadcastRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.exception.CopayerNotFoundException;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.exception.InsufficientFundsException;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.exception.InvalidAmountException;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.exception.InvalidWalletAddressException;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.login.LoginRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.publish.IPublishRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.signatures.ISignatureRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.ICreateWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.ICreateWalletResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.wallets.IJoinWalletResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.addresses.GsonAddressesResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.notification.GsonNotificationResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.publish.GsonPublishRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.signatures.GsonSignatureRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.transaction.GsonTransactionRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonCreateWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonCreateWalletResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonJoinWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonJoinWalletResponse;
import org.openkuva.kuvabase.bwcj.service.retrofit2utils.RequestFailedException;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;

public class Retrofit2BwsApiBridge implements IBitcoreWalletServerAPI {
    private final IRetrofit2BwsAPI serverAPI;

    public Retrofit2BwsApiBridge(IRetrofit2BwsAPI serverAPI) {
        this.serverAPI = serverAPI;
    }

    @Override
    public ICreateWalletResponse postWallets(ICreateWalletRequest createWalletRequest) {
        try {
            Response<GsonCreateWalletResponse> response = serverAPI
                    .postWallets(new GsonCreateWalletRequest(createWalletRequest))
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IAddressesResponse postAddresses(AddressesRequest addressesRequest) {
        try {
            Response<GsonAddressesResponse> response = serverAPI
                    .postAddresses(addressesRequest)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IJoinWalletResponse postWalletsWalletIdCopayers(String walletId, IJoinWalletRequest createCopayerRequest) {
        try {
            Response<GsonJoinWalletResponse> response = serverAPI
                    .postWalletsWalletIdCopayers(walletId, new GsonJoinWalletRequest(createCopayerRequest))
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IWallet getWallets(Map<String, String> options) {
        try {
            Response<GsonWallet> response = serverAPI
                    .getWallets(options)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else if (response.code() == 401) {
                String errorBody = response.errorBody().string();
                if (errorBody.contains("Copayer not found")) {
                    throw new CopayerNotFoundException("Copayer not found");
                }
                throw new RequestFailedException(response);
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ITransactionProposal postTxProposals(ITransactionRequest transactionRequest) {
        try {
            Response<GsonTransactionProposal> response = serverAPI
                    .postTxProposals(new GsonTransactionRequest(transactionRequest))
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                String errorBody = response.errorBody().string();
                if (errorBody.contains("INSUFFICIENT_FUNDS")) {
                    throw new InsufficientFundsException("INSUFFICIENT_FUNDS");
                } else if (errorBody.contains("INVALID_ADDRESS")) {
                    throw new InvalidWalletAddressException("INVALID_ADDRESS");
                } else if (errorBody.contains("Invalid amount")) {
                    throw new InvalidAmountException("Invalid amount");
                }
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GsonFeeLevel[] getFeeLevels(Map<String, String> options) {
        try {
            Response<GsonFeeLevel[]> response = serverAPI
                    .getFeeLevels(options)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GsonTransactionProposal postTxProposalsTxIdPublish(String txId, IPublishRequest publishRequest) {
        try {
            Response<GsonTransactionProposal> response = serverAPI
                    .postTxProposalsTxIdPublish(txId, new GsonPublishRequest(publishRequest))
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GsonTransactionProposal postTxProposalsTxIdSignatures(String txId, ISignatureRequest signatureRequest) {
        try {
            Response<GsonTransactionProposal> response = serverAPI
                    .postTxProposalsTxIdSignatures(txId, new GsonSignatureRequest(signatureRequest))
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GsonTransactionProposal postTxProposalsTxIdBroadcast(String txId, BroadcastRequest broadcastRequest) {
        try {
            Response<GsonTransactionProposal> response = serverAPI
                    .postTxProposalsTxIdBroadcast(txId, broadcastRequest)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ITransactionProposal deleteTxProposal(String txId) {
        try {
            Response<GsonTransactionProposal> response = serverAPI
                    .deleteTxProposal(txId)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ITransactionProposal[] getPendingTransactionProposals() {
        try {
            Response<GsonTransactionProposal[]> response = serverAPI
                    .getPendingTransactionProposals()
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GsonNotificationResponse[] getNotification(Map<String, String> options) {
        try {
            Response<GsonNotificationResponse[]> response = serverAPI
                    .notifications(options)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String login(LoginRequest request) {
        try {
            Response<String> response =
                    serverAPI.login(request).execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RequestFailedException(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
