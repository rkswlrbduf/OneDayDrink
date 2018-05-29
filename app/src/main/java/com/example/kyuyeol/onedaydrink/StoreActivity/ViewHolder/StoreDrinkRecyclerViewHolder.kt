package com.example.kyuyeol.onedaydrink.StoreActivity.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.DrinkInform
import kotlinx.android.synthetic.main.store_menu_row.view.*

class StoreDrinkRecyclerViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

    fun bind(list : ArrayList<DrinkInform>) {
        view.drink_name.text = list[adapterPosition].drinkName
    }

}