package com.example.kyuyeol.onedaydrink

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class RecyclerviewAdapter(val context : Context?, val barData: ArrayList<BarData>) : RecyclerView.Adapter<RecyclerviewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(barData[position], context)
    }

    override fun getItemCount(): Int {
        return barData.size
    }

    inner class Holder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        val store_name = itemview.store_name
        val store_detail_place = itemview.store_detail_place
        val store_distance = itemview.store_distance
        val store_image = itemview.store_image
        val store_place = itemview.store_place
        val store_service = itemview.store_service

        fun bind(barData : BarData, context : Context?) {
            store_name.text = barData.store_name
            store_detail_place.text = barData.store_detail_place
            store_distance.text = barData.store_distance
            store_image.setImageResource(context!!.resources.getIdentifier(barData.store_image, "drawable", context!!.packageName))
            store_place.text = barData.store_place
            store_service.text = barData.store_service
        }

    }

}