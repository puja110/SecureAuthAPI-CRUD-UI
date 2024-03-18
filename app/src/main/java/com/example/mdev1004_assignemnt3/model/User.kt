package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("email")
    val email: String,

    @SerializedName("id")
    val id: String
)


