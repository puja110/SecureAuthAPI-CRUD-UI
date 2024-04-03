package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class AddBookResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("savedBook")
    val savedBook: BookResponse
)
