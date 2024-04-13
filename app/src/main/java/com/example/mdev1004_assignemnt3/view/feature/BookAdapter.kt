package com.example.mdev1004_assignemnt3.view.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.databinding.ItemBookBinding
import com.example.mdev1004_assignemnt3.model.BookResponse

class BookAdapter(
    private var bookList: MutableList<BookResponse>,
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
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(bookList[position])

    }

    override fun getItemCount() = bookList.size
}