package com.example.mdev1004_assignemnt3

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://mdev1004-assignment3-4gv0.onrender.com"
    // Other BASE_URLs commented out for clarity

    // Lazy initialization of ApiService
    val getApiService: ApiService by lazy {
        // HttpLoggingInterceptor setup
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                // A place to store cookies received from the server
                private var cookies: List<Cookie> = ArrayList()

                // This method is called when the server sets cookies.
                // The OkHttp client calls this method to save cookies from the HTTP response.
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    this.cookies = cookies // Store the cookies for future requests
                }

                // This method is called before sending an HTTP request.
                // The OkHttp client calls this method to load cookies for the HTTP request.
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    // Return
                    return cookies
                }
            })
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        // Retrofit setup
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        // Create and return the ApiService instance
        retrofit.create(ApiService::class.java)
    }
}