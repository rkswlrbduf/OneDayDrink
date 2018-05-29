package com.example.kyuyeol.onedaydrink.StoreActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.DrinkInform
import com.example.kyuyeol.onedaydrink.StoreActivity.ViewHolder.StoreDrinkRecyclerViewHolder

class StoreDrinkRecyclerViewAdapter(val context : Context, val list : ArrayList<DrinkInform>) : RecyclerView.Adapter<StoreDrinkRecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreDrinkRecyclerViewHolder {
        return StoreDrinkRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.store_drink_row, parent, false))
    }

    override fun onBindViewHolder(holder: StoreDrinkRecyclerViewHolder, position: Int) {
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}