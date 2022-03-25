package com.example.mica.api

import com.example.mica.model.SendMessageRequest
import com.example.mica.model.SendMessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApi {

    @Headers(
        "Accept: application/json",
        "Content-type:application/json")
    @POST("/default/MicaReceiver")
    fun sendMessage(
        @Body sendMessageRequest: SendMessageRequest): Call<SendMessageResponse>
}