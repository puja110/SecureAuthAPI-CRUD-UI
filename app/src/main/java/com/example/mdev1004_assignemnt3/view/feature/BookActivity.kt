package com.example.mdev1004_assignemnt3.view.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.view.auth.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var logOff: FloatingActionButton
    private lateinit var addBook: FloatingActionButton
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        // Initializing ApiClient and Session Manager
        apiClient = ApiClient
        sessionManager = SessionManager(this)

        ivBack = findViewById(R.id.iv_back)
        logOff = findViewById(R.id.floatingActionButton)
        addBook = findViewById(R.id.floatingActionAddButton)

        // Allows the user to navigate back to the previous screen
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Redirect to the AddBookActivity
        addBook.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }

        // Log the user out from the app and navigate back to the login screen
        logOff.setOnClickListener {
            sessionManager.deleteAuthToken()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@BookActivity, "User logged off", Toast.LENGTH_LONG).show()
        }

        // Fetching list from the server
        getBookList()
    }

    private fun getBookList() {
        // getting access token from the server
        val accessToken = sessionManager.fetchAuthToken().toString()

        // fetching book data from the server
        apiClient.getApiService()
            .getBooks(token = "Bearer $accessToken")
            .enqueue(object : Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    // loading data in the recyclerview
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_book)
                    recyclerView.layoutManager = LinearLayoutManager(this@BookActivity)
                    recyclerView.adapter = BookAdapter((response.body()?.asReversed() ?: emptyList()).toMutableList(), sessionManager, apiClient)
                }

                override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                    Log.d("book response error", t.toString())
                }
            })
    }
}