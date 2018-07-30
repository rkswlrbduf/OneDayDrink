package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.NodeData
import com.example.kyuyeol.onedaydrink.R

class MainActivityAdapter(val context: Context, val list: List<NodeData.Data>) : RecyclerView.Adapter<MainActivityViewHolder>() {

    val INVISIBLE_CONTAINER = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val view = MainActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, parent, false))

        if (viewType == INVISIBLE_CONTAINER) {
            view.store_container.visibility = INVISIBLE
            return view
        }
        return view
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        if (position != 0 && position != list.size + 1) {
            holder.store_name.text = list[position - 1].name
            Glide.with(context).load("http://stou2.cafe24.com/image/" + list[position - 1].image + ".jpg").apply(RequestOptions().centerCrop()).into(holder.store_image)
            holder.store_drink_container.removeAllViews()
            if (list[position - 1].beer == 1) holder.store_drink_container.addView(LayoutInflater.from(context).inflate(R.layout.activity_main_drink_beer, holder.store_drink_container, false))
            if (list[position - 1].soju == 1) holder.store_drink_container.addView(LayoutInflater.from(context).inflate(R.layout.activity_main_drink_soju, holder.store_drink_container, false))
            if (list[position - 1].sansachun == 1) holder.store_drink_container.addView(LayoutInflater.from(context).inflate(R.layout.activity_main_drink_sansachun, holder.store_drink_container, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == list.size + 1) return INVISIBLE_CONTAINER
        return super.getItemViewType(position)
    }
}