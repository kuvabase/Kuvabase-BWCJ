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

package org.openkuva.kuvabase.bwcj.domain.utils.messageEncrypt;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SjclMessageEncryptor implements IMessageEncryptor {
    private final static String LIB_PATH = "sjcl.js";

    @Override
    public String encrypt(String msg, String encryptKey) {
        Global global = new Global();
        Context context = createAndInitializeContext(global);
        Scriptable scope = context.initStandardObjects(global);
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(LIB_PATH);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        try {
            compileAndExec(in, "classpath:" + LIB_PATH.toString(), context, scope);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String script = "var result = sjcl.encrypt(sjcl.codec.base64.toBits(\"" +
                encryptKey + "\"),'" +
                msg + "'," +
                "{ks: 128, iter: 1})";

        exec(script, "start", context, scope);
        Object result = scope.get("result", scope);
        String json = Context.toString(result);

        return json;
    }


    private static void exec(String script, String name, Context context, Scriptable scope) {
        context.compileString(script, name, 1, null).exec(context, scope);
    }

    private static void compileAndExec(Reader in, String name, Context rhinoContext, Scriptable scope) throws IOException {
        rhinoContext.compileReader(in, name, 1, null).exec(rhinoContext, scope);
    }

    private Context createAndInitializeContext(Global global) {
        Context context = ContextFactory.getGlobal().enterContext();
        global.init(context);
        context.setOptimizationLevel(-1);
        context.setLanguageVersion(Context.VERSION_1_5);
        return context;
    }

}
