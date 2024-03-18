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

    private lateinit var ivBack : ImageView
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val bookList : List<BookResponse>

        apiClient.getApiService().getBooks(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    Log.d("Test", response.toString())
                }

                override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        // This loop will create 20 Views
//        for (i in 1..10) {
//            bookList.add(BookResponse("", "Book Title", "Author Name", 4.5))
//        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_book)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = BookAdapter(bookList)
    }

}