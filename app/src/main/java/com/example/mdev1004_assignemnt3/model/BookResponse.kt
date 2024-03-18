package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("imageUrl")
    val thumbnail: String?,

    @SerializedName("booksName")
    val bookTitle: String?,

    @SerializedName("author")
    val bookAuthor: String?,

    @SerializedName("rating")
    val bookRating: Double,
)
