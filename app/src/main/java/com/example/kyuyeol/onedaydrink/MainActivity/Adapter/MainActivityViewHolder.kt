package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_main_list_item.view.*

class MainActivityViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

    val store_container = itemview.main_store_container
    val store_name = itemview.main_store_name
    val store_image = itemview.main_store_image
    val store_drink_container = itemview.main_store_drink_container
    val store_drink_beer = itemview.main_store_drink_beer
    val store_drink_soju = itemview.main_store_drink_soju
    val store_drink_sansachun = itemview.main_store_drink_sansachun
}