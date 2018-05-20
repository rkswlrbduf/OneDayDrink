package com.example.kyuyeol.onedaydrink

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class SmallRecyclerViewAdapter(val context : Context) : RecyclerView.Adapter<SmallRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallRecyclerViewHolder {
        return SmallRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.small_recyclerview_row, parent, false))
    }

    override fun onBindViewHolder(holder: SmallRecyclerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}