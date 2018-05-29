package com.example.kyuyeol.onedaydrink.MainActivity.ViewHolder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.store_type_row.view.*
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreRecyclerViewAdapter
import com.example.kyuyeol.onedaydrink.MainActivity.MainClass.StoreInform
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreActivity

class StoreTypeRecyclerViewHolder(val view : View, val context: Context) : RecyclerView.ViewHolder(view) {

    var storeList : ArrayList<StoreInform> = arrayListOf(StoreInform("한판 닭갈비", "10분", ", ★ 4.5"),
            StoreInform("왕코 등갈비", "5분", ", ★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"))

    val intent = Intent(context, StoreActivity::class.java)

    fun bind(list : ArrayList<String>) {
        view.little_title.setText(list[adapterPosition])
        view.little_recyclerview.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        view.little_recyclerview.adapter = StoreRecyclerViewAdapter(context, storeList) { storeInform ->
            context.startActivity(intent)
        }
    }
}