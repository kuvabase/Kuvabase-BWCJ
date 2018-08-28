
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

public class GsonAction implements IAction {

    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("createdOn")
    @Expose
    public int createdOn;
    @SerializedName("copayerId")
    @Expose
    public String copayerId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("signatures")
    @Expose
    public List<String> signatures = null;
    @SerializedName("xpub")
    @Expose
    public String xpub;
    @SerializedName("comment")
    @Expose
    public Object comment;

    public GsonAction() {
    }

    public GsonAction(IAction origin) {
        this.version = origin.getVersion();
        this.createdOn = origin.getCreatedOn();
        this.copayerId = origin.getCopayerId();
        this.type = origin.getType();
        this.signatures = origin.getSignatures();
        this.xpub = origin.getXpub();
        this.comment = origin.getComment();
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public int getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getCopayerId() {
        return copayerId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<String> getSignatures() {
        return signatures;
    }

    @Override
    public String getXpub() {
        return xpub;
    }

    @Override
    public Object getComment() {
        return comment;
    }
}
