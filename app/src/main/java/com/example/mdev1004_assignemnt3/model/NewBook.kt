package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class NewBook(


    @SerializedName("booksName")
    val bookName: String,

    @SerializedName("isbn")
    val isbn: String,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("author")
    val author: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("imageUrl")
    val imageUrl: String
)
