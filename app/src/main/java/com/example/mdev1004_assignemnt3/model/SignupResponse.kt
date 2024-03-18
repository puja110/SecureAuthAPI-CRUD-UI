package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("email")
    val email: String,

    @SerializedName("id")
    val id: String
)
