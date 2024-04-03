package com.example.mdev1004_assignemnt3.model

import com.google.gson.annotations.SerializedName

data class UpdateBook(

    @SerializedName("booksName")
    val bookName: String? = null,

    @SerializedName("isbn")
    val isbn: String? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("genre")
    val genre: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null


)