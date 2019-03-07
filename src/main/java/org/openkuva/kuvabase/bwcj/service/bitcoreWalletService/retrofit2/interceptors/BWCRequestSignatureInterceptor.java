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

package org.openkuva.kuvabase.bwcj.service.bitcoreWalletService.retrofit2.interceptors;

import org.openkuva.kuvabase.bwcj.data.entity.interfaces.credentials.ICredentials;
import org.openkuva.kuvabase.bwcj.domain.utils.CopayersCryptUtils;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static org.openkuva.kuvabase.bwcj.domain.utils.ListUtils.join;

public class BWCRequestSignatureInterceptor implements Interceptor {
    private final ICredentials credentials;
    private final CopayersCryptUtils copayersCryptUtils;
    private final String urlBws;

    public BWCRequestSignatureInterceptor(ICredentials credentials, CopayersCryptUtils copayersCryptUtils, String url) {
        this.credentials = credentials;
        this.copayersCryptUtils = copayersCryptUtils;
        this.urlBws = url;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldReq = chain.request();
        Request newReq =
                oldReq
                        .newBuilder()
                        .addHeader(
                                "x-identity",
                                copayersCryptUtils.copayerId(
                                        credentials.getSeed(),
                                        credentials.getNetworkParameters()))
                        .addHeader("x-signature",
                                signRequest(
                                        oldReq.method().toLowerCase(),
                                        extractUrl(oldReq.url().toString()),
                                        bodyToString(oldReq.body()),
                                        copayersCryptUtils.requestDerivation(
                                                credentials.getSeed())
                                                .getPrivateKeyAsHex()))
                        .build();

        return chain.proceed(newReq);
    }

    private String extractUrl(String url) {
        return url.substring(urlBws.length() - 1, url.length());
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "{}";
            return buffer.readUtf8();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String signRequest(String method, String url, String arg, String key) {
        return
                copayersCryptUtils.signMessage(
                        join(
                                Arrays.asList(
                                        method,
                                        url,
                                        arg),
                                "|"),
                        key);
    }


}
