package com.example.mdev1004_assignemnt3.view.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.DeleteBookResponse
import com.example.mdev1004_assignemnt3.model.UpdateBook
import com.example.mdev1004_assignemnt3.model.UpdateBookResponse
import com.example.mdev1004_assignemnt3.view.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateBookActivity: AppCompatActivity() {

    private lateinit var bookNameEditText: EditText
    private lateinit var ratingEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var ivBack: ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_book)

        // Create an instances of ApiClient and Session Manager
        sessionManager = SessionManager(this)
        apiClient = ApiClient

        // Retrieve the book data from the intent
        val book = intent.getStringArrayExtra("book")

        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Initialize EditText fields
        bookNameEditText = findViewById(R.id.bookNameEditText)
        ratingEditText = findViewById(R.id.ratingEditText)
        authorEditText = findViewById(R.id.authorEditText)

        Log.d("Book Id", book?.get(0).toString())

        // Populate the fields with the book data
        bookNameEditText.setText(book?.get(1))
        ratingEditText.setText(book?.get(2))
        authorEditText.setText(book?.get(3))

        // Handle update button click
        val updateButton = findViewById<Button>(R.id.updateButton)

        // getting access token from the server
        val accessToken = sessionManager.fetchAuthToken().toString()

        // Set OnClickListener for the update book button
        updateButton.setOnClickListener {
            apiClient.getApiService().updateBook(   // Call the deleteBook function in the API service
                id = book?.get(0).toString(),
                token = "Bearer $accessToken",
                updatedBook = UpdateBook(bookName = bookNameEditText.text.toString(),
                    rating =   ratingEditText.text.toString().toDouble(),
                    author =   authorEditText.text.toString()
                    )
            )
            .enqueue(object : Callback<UpdateBookResponse> {
                override fun onResponse(
                    call: Call<UpdateBookResponse>,
                    response: Response<UpdateBookResponse>
                ) {
                    if(response.isSuccessful){
                        // Navigate to the BookActivity after successful book update
                        val intent = Intent(this@UpdateBookActivity, BookActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this@UpdateBookActivity, "Book Updated", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this@UpdateBookActivity, "Book Update Failure", Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<UpdateBookResponse>, t: Throwable) {
                    Toast.makeText(this@UpdateBookActivity, "Book Update Failure", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}