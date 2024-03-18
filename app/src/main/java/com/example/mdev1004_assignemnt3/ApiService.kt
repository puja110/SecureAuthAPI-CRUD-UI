package com.example.mdev1004_assignemnt3

import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.model.LoginRequest
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.model.SignupRequest
import com.example.mdev1004_assignemnt3.model.SignupResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("books/")
    fun getComments(): Call<List<BookResponse>>

    @POST("auth/login")
    @FormUrlEncoded
    fun login(@Body request: LoginRequest): Call<LoginResponse>


    @POST("auth/signup")
    @FormUrlEncoded
    fun login(@Body request: SignupRequest): Call<SignupResponse>

}