package com.example.mdev1004_assignemnt3.view.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.model.BookResponse

class BookActivity : AppCompatActivity() {

    private lateinit var ivBack : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val bookList = ArrayList<BookResponse>()

        // This loop will create 20 Views
        for (i in 1..10) {
            bookList.add(BookResponse("Book Title", "Author Name", 4.5))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_book)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookAdapter(bookList)
    }
}