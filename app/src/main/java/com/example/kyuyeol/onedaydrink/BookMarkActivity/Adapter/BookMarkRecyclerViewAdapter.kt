package com.example.kyuyeol.onedaydrink.BookMarkActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.BookMarkActivity.ViewHolder.BookMarkRecyclerViewHolder
import com.example.kyuyeol.onedaydrink.R

class BookMarkRecyclerViewAdapter(val context : Context) : RecyclerView.Adapter<BookMarkRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkRecyclerViewHolder {
        return BookMarkRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.bookmark_row, parent, false))
    }

    override fun onBindViewHolder(holder: BookMarkRecyclerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}