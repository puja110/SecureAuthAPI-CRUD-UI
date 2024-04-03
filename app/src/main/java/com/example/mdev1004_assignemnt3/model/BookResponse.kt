package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("_id")
    val id: String?,

    @SerializedName("booksName")
    val bookName: String?,

    @SerializedName("isbn")
    val isbn: String?,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("author")
    val author: String?,

    @SerializedName("genre")
    val genre: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("__v")
    val version: Int
)
