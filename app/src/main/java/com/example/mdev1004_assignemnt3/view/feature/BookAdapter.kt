package com.example.mdev1004_assignemnt3.view.feature

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.databinding.ItemBookBinding
import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.model.DeleteBookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAdapter(
    private var bookList: MutableList<BookResponse>,
    private var sessionManager: SessionManager,
    private var apiClient: ApiClient
) :
    RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    // ViewHolder class to hold the view components for each item in the RecyclerView
    inner class MyViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookResponse?) {
            // Setting the values to the views
            binding.tvBookTitle.text = data?.bookName
            binding.tvBookId.text = data?.id
            binding.tvBookAuthor.text = data?.author
            binding.tvBookRating.text = data?.rating.toString()

            // Load the book image using Glide library
            Glide.with(binding.root.context)
                .load(data?.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background).into(binding.ivBookImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MyViewHolder(binding)

        // Get references to the views in the ViewHolder
        val bookCard = viewHolder.itemView.findViewById<CardView>(R.id.book_card)
        val deleteBookButton = viewHolder.itemView.findViewById<Button>(R.id.delete_book_button)
        val editBookButton = viewHolder.itemView.findViewById<Button>(R.id.update_book_button)

        // Get the access token from the session manager
        val accessToken = sessionManager.fetchAuthToken().toString()

        // Set OnClickListener on card to navigate to UpdateBookActivity when a book card is clicked
        bookCard.setOnClickListener {
            val intent = Intent(binding.root.context, UpdateBookActivity::class.java).apply {
                // Pass book details to UpdateBookActivity
                val book = arrayOf( bookCard.findViewById<TextView>(R.id.tv_book_id).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_title).text.toString(),
                     bookCard.findViewById<TextView>(R.id.tv_book_rating).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_author).text.toString()
                   )
                putExtra("book", book)
            }
            // Start the UpdateBookActivity
            binding.root.context.startActivity(intent)
        }

        // Set OnClickListener on edit button to navigate to UpdateBookActivity when a book card is clicked
        editBookButton.setOnClickListener {
            val intent = Intent(binding.root.context, UpdateBookActivity::class.java).apply {
                // Pass book details to UpdateBookActivity
                val book = arrayOf( bookCard.findViewById<TextView>(R.id.tv_book_id).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_title).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_rating).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_author).text.toString()
                )
                putExtra("book", book)
            }
            // Start the UpdateBookActivity
            binding.root.context.startActivity(intent)
        }

        // Set OnClickListener for the delete book button
        deleteBookButton.setOnClickListener {
            apiClient = ApiClient   // Create an instance of ApiClient
            apiClient.getApiService().deleteBook(   // Call the deleteBook function in the API service
                id = bookCard.findViewById<TextView>(R.id.tv_book_id).text.toString(),
                token = "Bearer $accessToken"
            )
                .enqueue(object : Callback<DeleteBookResponse>{
                    override fun onResponse(
                        call: Call<DeleteBookResponse>,
                        response: Response<DeleteBookResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Remove the deleted book from the dataset
                            val position = viewHolder.adapterPosition
                            bookList.removeAt(position)
                            notifyItemRemoved(position)

                            Toast.makeText(binding.root.context, "Book deleted successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(binding.root.context, "Failed to delete book", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DeleteBookResponse>, t: Throwable) {
                        // Show an error message if the request fails
                        Toast.makeText(binding.root.context, "Failed to delete book", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(bookList[position])

    }

    override fun getItemCount() = bookList.size
}