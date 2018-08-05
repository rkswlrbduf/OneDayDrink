package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.content.Context
import android.os.Trace
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.NodeData
import com.example.kyuyeol.onedaydrink.R

class MainActivityAdapter(val list: List<NodeData.Data>) : RecyclerView.Adapter<MainActivityViewHolder>() {

    val INVISIBLE_VIEW = 1
    var context : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        Trace.beginSection("Adapter onCreateViewHolder")
        context = parent.context
        val view = MainActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, parent, false))
        if(viewType == INVISIBLE_VIEW) view.store_container.visibility = INVISIBLE
        Trace.endSection()
        return view
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        Trace.beginSection("Adapter onBindViewHolder" + position)
        if (position != 0 && position != list.size + 1) {
            holder.store_name.text = list[position - 1].name
            Glide.with(context!!).load("http://stou2.cafe24.com/image/" + list[position - 1].image + ".jpg").apply(RequestOptions().centerCrop()).thumbnail(0.1f).into(holder.store_image)
            if (list[position - 1].beer == 0) holder.store_drink_beer.visibility = View.GONE
            if (list[position - 1].soju == 0) holder.store_drink_soju.visibility = View.GONE
            if (list[position - 1].sansachun == 0) holder.store_drink_sansachun.visibility = View.GONE
        }
        Trace.endSection()
    }

    override fun getItemCount(): Int {
        return list.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == position + 1) return INVISIBLE_VIEW
        return super.getItemViewType(position)
    }
}