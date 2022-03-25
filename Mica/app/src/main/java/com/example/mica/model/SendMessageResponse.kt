package com.example.mica.model

import com.google.gson.annotations.SerializedName

class SendMessageResponse {

    var message = ""

    @SerializedName("slot_to_elicit")
    var slotToElicit = ""

    @SerializedName("msg_rsp")
    var msgRsp = ""
}