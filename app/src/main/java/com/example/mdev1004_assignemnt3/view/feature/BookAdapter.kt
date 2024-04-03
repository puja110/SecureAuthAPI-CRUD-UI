package com.example.mdev1004_assignemnt3.view.feature

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.databinding.ItemBookBinding
import com.example.mdev1004_assignemnt3.model.BookResponse
import com.example.mdev1004_assignemnt3.model.DeleteBookResponse
import com.example.mdev1004_assignemnt3.model.UpdateBook
import com.example.mdev1004_assignemnt3.view.auth.LoginActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAdapter(
    private var bookList: MutableList<BookResponse>,
    private var sessionManager: SessionManager,
    private var apiClient: ApiClient
) :
    RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookResponse?) {
            binding.tvBookTitle.text = data?.bookName
            binding.tvBookId.text = data?.id

            Glide.with(binding.root.context)
                .load(data?.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background).into(binding.ivBookImage)

            binding.tvBookAuthor.text = data?.author
            binding.tvBookRating.text = data?.rating.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MyViewHolder(binding)

        val bookCard = viewHolder.itemView.findViewById<CardView>(R.id.book_card)

        val deleteBookButton = viewHolder.itemView.findViewById<Button>(R.id.delete_book_button)

        // getting access token from the server
        val accessToken = sessionManager.fetchAuthToken().toString()

        bookCard.setOnClickListener {
            val intent = Intent(binding.root.context, UpdateBookActivity::class.java).apply {
                val book = arrayOf( bookCard.findViewById<TextView>(R.id.tv_book_id).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_title).text.toString(),
                     bookCard.findViewById<TextView>(R.id.tv_book_rating).text.toString(),
                    bookCard.findViewById<TextView>(R.id.tv_book_author).text.toString()
                   )
                putExtra("book", book)
            }
            binding.root.context.startActivity(intent)
            Toast.makeText(binding.root.context, "User logged off", Toast.LENGTH_LONG).show()
        }


        deleteBookButton.setOnClickListener {
            apiClient = ApiClient
            apiClient.getApiService().deleteBook(
                id = bookCard.findViewById<TextView>(R.id.tv_book_id).text.toString(),
                token = "Bearer $accessToken"
            )
                .enqueue(object : Callback<DeleteBookResponse>{
                    override fun onResponse(
                        call: Call<DeleteBookResponse>,
                        response: Response<DeleteBookResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Remove the item from the dataset
                            val position = viewHolder.adapterPosition
                            bookList.removeAt(position)
                            notifyItemRemoved(position)
                            Toast.makeText(binding.root.context, "Book deleted successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(binding.root.context, "Failed to delete book", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DeleteBookResponse>, t: Throwable) {
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