package com.example.kyuyeol.onedaydrink.MainActivity.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.NodeData
import kotlinx.android.synthetic.main.activity_main_list_item.view.*

class MainActivityViewHolder(val itemview : View) : RecyclerView.ViewHolder(itemview), View.OnClickListener {

    val store_container = itemview.main_store_container

    fun bind(data : NodeData.Data, listener : MainActivity.onItemClickListener) {
        itemview.main_store_name.text = data.name
        Glide.with(itemview.context).load("http://stou2.cafe24.com/image/" + data.image + ".jpg").apply(RequestOptions().centerCrop()).thumbnail(0.01f).into(itemview.main_store_image)
        if (data.beer == 0) itemview.store_store_drink_beer.visibility = View.GONE
        if (data.soju == 0) itemview.store_store_drink_soju.visibility = View.GONE
        if (data.sansachun == 0) itemview.store_store_drink_sansachun.visibility = View.GONE

        itemview.main_store_container.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener.onItemClick(data)
            }
        })
    }

    override fun onClick(v: View?) {

    }
}