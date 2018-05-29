package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.MainActivity.MainClass.StoreInform
import com.example.kyuyeol.onedaydrink.MainActivity.ViewHolder.StoreRecyclerViewHolder
import com.example.kyuyeol.onedaydrink.R

class StoreRecyclerViewAdapter(val context : Context, val list : ArrayList<StoreInform>, val itemClick: (StoreInform) -> Unit) : RecyclerView.Adapter<StoreRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreRecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.store_row, parent, false)
        return StoreRecyclerViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: StoreRecyclerViewHolder, position: Int) {
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}