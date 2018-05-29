package com.example.kyuyeol.onedaydrink.StoreActivity.Adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.MenuInform
import com.example.kyuyeol.onedaydrink.StoreActivity.ViewHolder.StoreMenuRecyclerViewHolder

class StoreMenuRecyclerViewAdapter(val context: Context, val list : ArrayList<MenuInform>) : RecyclerView.Adapter<StoreMenuRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuRecyclerViewHolder {
        return StoreMenuRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.store_menu_row, parent, false))
    }

    override fun onBindViewHolder(holder: StoreMenuRecyclerViewHolder, position: Int) {
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}