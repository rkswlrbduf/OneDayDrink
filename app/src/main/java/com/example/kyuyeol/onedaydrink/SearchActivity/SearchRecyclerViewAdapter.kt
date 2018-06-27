package com.example.kyuyeol.onedaydrink.SearchActivity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.R

class SearchRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<SearchRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclerViewHolder {
        return SearchRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.search_row, parent, false))
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}