package com.example.kyuyeol.onedaydrink

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class SmallRecyclerViewAdapter(val context : Context, val list : ArrayList<StoreInform>, val itemClick: (StoreInform) -> Unit) : RecyclerView.Adapter<SmallRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallRecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.small_recyclerview_row, parent, false)
        return SmallRecyclerViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: SmallRecyclerViewHolder, position: Int) {
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}