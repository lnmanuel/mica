package com.example.mica.model

import com.google.gson.annotations.SerializedName

class Clinic(var uid: String, @SerializedName("clinic") var clinicName: String, var address: String, @SerializedName("name") var doctor: String, @SerializedName("coords") var longLat: String) {
}