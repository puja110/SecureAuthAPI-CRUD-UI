package com.example.mdev1004_assignemnt3.view.feature

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.AddBookResponse
import com.example.mdev1004_assignemnt3.model.NewBook
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookActivity : AppCompatActivity() {

    private lateinit var bookNameEditText: EditText
    private lateinit var ratingEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var isbnEditText: EditText
    private lateinit var genreEditText: EditText
    private lateinit var ivBack: ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        sessionManager = SessionManager(this)

        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Initialize EditText fields
        bookNameEditText = findViewById(R.id.bookNameEditText)
        ratingEditText = findViewById(R.id.ratingEditText)
        authorEditText = findViewById(R.id.authorEditText)
        isbnEditText = findViewById(R.id.isbnEditText)
        genreEditText = findViewById(R.id.genreEditText)


        // Handle update button click
        val updateButton = findViewById<Button>(R.id.addButton)
        // getting access token from the server
        val accessToken = sessionManager.fetchAuthToken().toString()

        updateButton.setOnClickListener {
            apiClient = ApiClient
            apiClient.getApiService().addBook(
                token = "Bearer $accessToken",
                book = NewBook(
                    bookName = bookNameEditText.text.toString(),
                    rating = ratingEditText.text.toString().toDouble(),
                    author = authorEditText.text.toString(),
                    isbn = isbnEditText.text.toString(),
                    genre = genreEditText.text.toString(),
                    imageUrl = "https://images.unsplash.com/photo-1639690283395-b62444cf9a76?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                )
            ).enqueue(object : Callback<AddBookResponse> {
                override fun onResponse(
                    call: Call<AddBookResponse>,
                    response: Response<AddBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@AddBookActivity, BookActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@AddBookActivity, "Book Added", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@AddBookActivity, "Book Add Failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                    Toast.makeText(this@AddBookActivity, "Book Add Failure", Toast.LENGTH_LONG)
                        .show()
                }

            }
            )

        }
    }
}