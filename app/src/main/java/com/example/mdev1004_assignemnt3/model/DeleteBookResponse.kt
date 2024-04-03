package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class DeleteBookResponse (

    @SerializedName("delete")
    val delete: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("deletedBook")
    val deletedBook: BookResponse

)