package com.example.kyuyeol.onedaydrink.StoreActivity.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.MenuInform
import kotlinx.android.synthetic.main.store_menu_row.view.*

class StoreMenuRecyclerViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

    fun bind(list : ArrayList<MenuInform>) {
        view.drink_name.text = list[adapterPosition].menuName
        view.menu_price.text = list[adapterPosition].menuPrice
    }

}