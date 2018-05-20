package com.example.kyuyeol.onedaydrink

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.recyclerview_row.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import android.content.Context
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL

class RecyclerViewHolder(val view : View, val context: Context) : RecyclerView.ViewHolder(view) {

    fun bind() {
        view.little_recyclerview.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        view.little_recyclerview.adapter = SmallRecyclerViewAdapter(context)
    }

}