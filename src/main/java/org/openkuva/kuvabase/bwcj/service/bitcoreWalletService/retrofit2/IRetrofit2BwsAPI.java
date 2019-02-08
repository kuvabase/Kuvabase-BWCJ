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
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.address.AddressesRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.broadcast.BroadcastRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.interfaces.login.LoginRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.addresses.GsonAddressesResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.notification.GsonNotificationResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.publish.GsonPublishRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.signatures.GsonSignatureRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.transaction.GsonTransactionRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonCreateWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonCreateWalletResponse;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonJoinWalletRequest;
import org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.gson.wallets.GsonJoinWalletResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IRetrofit2BwsAPI {

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v2/wallets/")
    Call<GsonCreateWalletResponse> postWallets(
            @Body GsonCreateWalletRequest createWalletRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v3/addresses/")
    Call<GsonAddressesResponse> postAddresses(
            @Body AddressesRequest addressesRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @GET("v1/addresses/")
    Call<GsonAddressesResponse[]> getAddresses();

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v2/wallets/{wallet_id}/copayers")
    Call<GsonJoinWalletResponse> postWalletsWalletIdCopayers(
            @Path(value = "wallet_id", encoded = true) String walletId,
            @Body GsonJoinWalletRequest createCopayerRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @GET("v2/wallets/")
    Call<GsonWallet> getWallets(
            @QueryMap Map<String, String> options);


    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v2/txproposals/")
    Call<GsonTransactionProposal> postTxProposals(
            @Body GsonTransactionRequest transactionRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @GET("v1/feelevels")
    Call<GsonFeeLevel[]> getFeeLevels(
            @QueryMap Map<String, String> options);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v1/txproposals/{tx_id}/publish")
    Call<GsonTransactionProposal> postTxProposalsTxIdPublish(
            @Path(value = "tx_id", encoded = true) String txId,
            @Body GsonPublishRequest publishRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v1/txproposals/{tx_id}/signatures")
    Call<GsonTransactionProposal> postTxProposalsTxIdSignatures(
            @Path(value = "tx_id", encoded = true) String txId,
            @Body GsonSignatureRequest signatureRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v1/txproposals/{tx_id}/broadcast")
    Call<GsonTransactionProposal> postTxProposalsTxIdBroadcast(
            @Path(value = "tx_id", encoded = true) String txId,
            @Body BroadcastRequest broadcastRequest);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @DELETE("v1/txproposals/{tx_id}/")
    Call<GsonTransactionProposal> deleteTxProposal(
            @Path(value = "tx_id", encoded = true) String txId);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @GET("v1/txproposals/")
    Call<GsonTransactionProposal[]> getPendingTransactionProposals();

    @GET("v1/notifications/")
    Call<GsonNotificationResponse[]> notifications(@QueryMap Map<String, String> options);

    @Headers({"Content-Type:application/json", "x-client-version:bwc-5.1.2"})
    @POST("v1/login/")
    Call<String> login(@Body LoginRequest request);
}