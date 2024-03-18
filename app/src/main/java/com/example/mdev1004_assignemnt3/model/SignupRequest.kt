package com.example.mdev1004_assignemnt3.model

import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("email")
    val email: TextInputEditText,

    @SerializedName("password")
    val password: TextInputEditText
)

