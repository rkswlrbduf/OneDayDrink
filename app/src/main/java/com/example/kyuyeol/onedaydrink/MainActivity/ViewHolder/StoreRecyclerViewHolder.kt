package com.example.kyuyeol.onedaydrink.MainActivity.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kyuyeol.onedaydrink.MainActivity.MainClass.StoreInform
import kotlinx.android.synthetic.main.store_row.view.*

class StoreRecyclerViewHolder(val view : View, val itemClick : (StoreInform) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(list : ArrayList<StoreInform>) {
        view.menu_name.setText(list[adapterPosition].store_name)
        view.store_time.setText(list[adapterPosition].store_time)
        view.store_star.setText(list[adapterPosition].store_star)

        view.setOnClickListener{itemClick(list[adapterPosition])}

    }

}