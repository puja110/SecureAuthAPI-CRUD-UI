package com.example.mdev1004_assignemnt3.view.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.databinding.ItemBookBinding
import com.example.mdev1004_assignemnt3.model.BookResponse

class BookAdapter(
    private var bookList: List<BookResponse>,
) :
    RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookResponse?) {
            binding.tvBookTitle.text = data?.bookTitle

            Glide.with(binding.root.context)
                .load(data?.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background).into(binding.ivBookImage)

            binding.tvBookAuthor.text = data?.bookAuthor
            binding.tvBookRating.text = data?.bookRating.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = MyViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        val bookCard = v.itemView.findViewById<CardView>(R.id.book_card)

        bookCard.setOnClickListener {

        }

        return v
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(bookList[position])

    }

    override fun getItemCount() = bookList.size
}