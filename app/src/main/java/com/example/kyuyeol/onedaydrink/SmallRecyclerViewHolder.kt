package com.example.kyuyeol.onedaydrink

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.recyclerview_row.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import android.content.Context
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.small_recyclerview_row.view.*

class SmallRecyclerViewHolder(val view : View, val itemClick : (StoreInform) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(list : ArrayList<StoreInform>) {
        view.store_name.setText(list[adapterPosition].store_name)
        view.store_time.setText(list[adapterPosition].store_time)
        view.store_star.setText(list[adapterPosition].store_star)

        view.setOnClickListener{itemClick(list[adapterPosition])}

    }

}