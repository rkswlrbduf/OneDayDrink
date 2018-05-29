package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.MainActivity.ViewHolder.StoreTypeRecyclerViewHolder
import com.example.kyuyeol.onedaydrink.R

class StoreTypeRecyclerViewAdapter(val context : Context, val list : ArrayList<String>) : RecyclerView.Adapter<StoreTypeRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreTypeRecyclerViewHolder {
        return StoreTypeRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.store_type_row, parent, false), context)
    }

    override fun onBindViewHolder(holder: StoreTypeRecyclerViewHolder, position: Int) {
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}