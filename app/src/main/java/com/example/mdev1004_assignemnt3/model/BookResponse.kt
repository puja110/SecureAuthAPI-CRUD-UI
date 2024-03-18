package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("status_code")
    val thumbnail: String?,

    @SerializedName("bookTitle")
    val bookTitle: String?,

    @SerializedName("bookAuthor")
    val bookAuthor: String?,

    @SerializedName("bookRating")
    val bookRating: Double,
)
