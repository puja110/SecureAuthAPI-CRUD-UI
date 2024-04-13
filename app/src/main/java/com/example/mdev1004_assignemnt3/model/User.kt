package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("username")
    val username: String,

    @SerializedName("id")
    val id: String
)


