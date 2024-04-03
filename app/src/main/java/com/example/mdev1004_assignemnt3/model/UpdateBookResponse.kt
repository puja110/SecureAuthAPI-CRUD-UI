package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class UpdateBookResponse (

    @SerializedName("message")
    val message: String,

    @SerializedName("updatedBook")
    val updatedBook: BookResponse

)