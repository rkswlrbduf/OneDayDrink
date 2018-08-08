package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.content.Context
import android.os.Trace
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.NodeData
import com.example.kyuyeol.onedaydrink.R

class MainActivityAdapter(val list: List<NodeData.Data>, val clickListener : MainActivity.onItemClickListener) : RecyclerView.Adapter<MainActivityViewHolder>() {

    val INVISIBLE_VIEW = 1
    var context : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        context = parent.context
        val view = MainActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, parent, false))
        if(viewType == INVISIBLE_VIEW) view.store_container.visibility = INVISIBLE
        return view
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        Log.w("TAG", "ONBINDVIEWHOLDER")
        if (position != 0 && position != list.size + 1) {
            holder.bind(list[position-1], clickListener)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == list.size + 1) return INVISIBLE_VIEW
        return super.getItemViewType(position)
    }
}