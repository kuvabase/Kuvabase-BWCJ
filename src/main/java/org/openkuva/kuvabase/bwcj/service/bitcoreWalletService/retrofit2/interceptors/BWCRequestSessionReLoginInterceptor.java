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

import java.io.IOException;

import org.openkuva.kuvabase.bwcj.data.repository.exception.NotFoundException;
import org.openkuva.kuvabase.bwcj.data.repository.interfaces.session.IXSessionRepository;
import org.openkuva.kuvabase.bwcj.domain.useCases.login.ILoginUseCase;
import okhttp3.Interceptor;
import okhttp3.Response;

public class BWCRequestSessionReLoginInterceptor implements Interceptor {
    private final IXSessionRepository sessionRepository;
    private final ILoginUseCase loginUseCase;

    public BWCRequestSessionReLoginInterceptor(IXSessionRepository sessionRepository, ILoginUseCase loginUseCase) {
        this.sessionRepository = sessionRepository;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Response response =
                    chain.proceed(
                            new RequestWithXSessionHeader(
                                    chain.request(),
                                    sessionRepository
                                            .load()
                                            .sessionId())
                                    .asRequest());

            if (response.code() == 401) {
                return
                        chain.proceed(
                                new RequestWithXSessionHeader(
                                        chain.request(),
                                        loginUseCase
                                                .execute()
                                                .sessionId())
                                        .asRequest());
            } else {
                return response;
            }
        } catch (NotFoundException e) {
            return chain.proceed(chain.request());
        } catch (Exception e) {
            e.printStackTrace();
            return chain.proceed(chain.request());
        }
    }
}
