package com.example.kyuyeol.onedaydrink.EventActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.kyuyeol.onedaydrink.EventActivity.Adapter.EventRecyclerViewAdapter
import com.example.kyuyeol.onedaydrink.R
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        event_recyclerview.adapter = EventRecyclerViewAdapter(this)
        event_recyclerview.layoutManager = LinearLayoutManager(this)

    }
}