package com.example.mdev1004_assignemnt3

import com.example.mdev1004_assignemnt3.model.AddBookResponse
import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.model.DeleteBookResponse
import com.example.mdev1004_assignemnt3.model.LoginRequest
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.model.NewBook
import com.example.mdev1004_assignemnt3.model.SignupRequest
import com.example.mdev1004_assignemnt3.model.SignupResponse
import com.example.mdev1004_assignemnt3.model.UpdateBook
import com.example.mdev1004_assignemnt3.model.UpdateBookResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @GET("books/")
    fun getBooks(@Header ("Authorization") token: String): Call<List<BookResponse>>

    @POST("books/")
    fun addBook(@Header ("Authorization") token: String,
                @Body book: NewBook): Call<AddBookResponse>

    @PATCH("books/{id}")
    fun updateBook(@Header ("Authorization") token: String,
                   @Body updatedBook: UpdateBook,
                   @Path("id") id: String): Call<UpdateBookResponse>

    @DELETE("books/{id}")
    fun deleteBook(@Header ("Authorization") token: String,
                   @Path("id") id: String): Call<DeleteBookResponse>
}