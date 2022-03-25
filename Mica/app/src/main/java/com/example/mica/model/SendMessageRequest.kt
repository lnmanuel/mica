package com.example.mica.model

import com.google.gson.annotations.SerializedName

class SendMessageRequest(@SerializedName("userid") var userId: String, var text: String) {

}