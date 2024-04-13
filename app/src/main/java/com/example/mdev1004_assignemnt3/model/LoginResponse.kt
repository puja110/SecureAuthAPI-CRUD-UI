package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("message")
    val message: String,
)
