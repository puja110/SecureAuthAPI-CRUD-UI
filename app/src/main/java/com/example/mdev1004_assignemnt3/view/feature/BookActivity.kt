package com.example.mdev1004_assignemnt3.view.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_book)

        apiClient = ApiClient
        sessionManager = SessionManager(this)
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val accessToken = sessionManager.fetchAuthToken().toString()

        apiClient.getApiService()
            .getBooks(token = "Bearer $accessToken")
            .enqueue(object : Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_book)
                    recyclerView.layoutManager = LinearLayoutManager(this@BookActivity)
                    recyclerView.adapter = BookAdapter(response.body() ?: emptyList())
                }

                override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                    Log.d("book response error", t.toString())
                }
            })
    }

}