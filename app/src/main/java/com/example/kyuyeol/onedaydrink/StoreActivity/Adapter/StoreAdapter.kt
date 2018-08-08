package com.example.kyuyeol.onedaydrink.StoreActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.StoreTextMenuInform
import kotlinx.android.synthetic.main.store_menu_item.view.*

class StoreAdapter(private val context: Context, private val data: ArrayList<StoreTextMenuInform>) : RecyclerView.Adapter<StoreAdapter.StoreTextMenuViewHolder>() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreTextMenuViewHolder {
        return StoreTextMenuViewHolder(inflater.inflate(R.layout.store_menu_item, parent, false))
    }

    override fun onBindViewHolder(holder: StoreTextMenuViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class StoreTextMenuViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bind(storeTextMenuInform: StoreTextMenuInform) {
            itemview.store_text_menu_name.text = storeTextMenuInform.store_text_menu_name
            itemview.store_text_menu_price.text = storeTextMenuInform.store_text_menu_price
        }

    }

}