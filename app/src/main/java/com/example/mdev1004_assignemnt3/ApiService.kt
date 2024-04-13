package com.example.mdev1004_assignemnt3

import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.model.LoginRequest
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.model.LogoutResponse
import com.example.mdev1004_assignemnt3.model.SignupRequest
import com.example.mdev1004_assignemnt3.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @GET("books/")
    fun getBooks(): Call<List<BookResponse>>

    @GET("auth/logout")
    fun logout(): Call<LogoutResponse>
}