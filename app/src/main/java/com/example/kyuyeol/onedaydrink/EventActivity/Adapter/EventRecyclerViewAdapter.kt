package com.example.kyuyeol.onedaydrink.EventActivity.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyuyeol.onedaydrink.EventActivity.ViewHolder.EventRecyclerViewHolder
import com.example.kyuyeol.onedaydrink.R

class EventRecyclerViewAdapter(val context : Context) : RecyclerView.Adapter<EventRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRecyclerViewHolder {
        return EventRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.event_row, parent, false))
    }

    override fun onBindViewHolder(holder: EventRecyclerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

}