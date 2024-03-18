package com.example.mdev1004_assignemnt3.model

import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("email")
    var email: TextInputEditText,

    @SerializedName("password")
    var password: TextInputEditText
)
