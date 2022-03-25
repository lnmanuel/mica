package com.example.mica.api

import com.example.mica.model.Clinic
import com.example.mica.model.GetClinicsRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClinicApi {

    @Headers(
        "Accept: application/json",
        "Content-type:application/json")
    @POST("/default/ListofDoc")
    fun getClinics(
        @Body getClinicsRequest: GetClinicsRequest
    ): Call<List<Clinic>>
}