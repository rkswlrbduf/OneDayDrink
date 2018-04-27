package com.example.kyuyeol.onedaydrink

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecyclerviewAdapter(val context : Context?) : RecyclerView.Adapter<RecyclerviewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class Holder(itemview : View?) : RecyclerView.ViewHolder(itemview) {



    }

}