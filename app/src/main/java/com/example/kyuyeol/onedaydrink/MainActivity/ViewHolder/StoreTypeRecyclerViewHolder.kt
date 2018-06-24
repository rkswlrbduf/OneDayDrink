package com.example.kyuyeol.onedaydrink.MainActivity.ViewHolder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.store_type_row.view.*
import android.content.Context
import android.content.Intent
import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreRecyclerViewAdapter
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.MainActivity.MainClass.StoreInform
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs

class StoreTypeRecyclerViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {

    var storeList: ArrayList<StoreInform> = arrayListOf(StoreInform("한판 닭갈비", "10분", ", ★ 4.5"),
            StoreInform("왕코 등갈비", "5분", ", ★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"))

    val intent = Intent(context, StoreActivity::class.java)
    val TAG = "StoreTypeRecycler_Log"

    var startX: Float? = null
    var startY: Float? = null
    var currentX: Float? = null
    var currentY: Float? = null

    fun bind(list: ArrayList<String>) {
        view.little_title.setText(list[adapterPosition])
        view.little_recyclerview.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        view.little_recyclerview.adapter = StoreRecyclerViewAdapter(context, storeList) { storeInform ->
            context.startActivity(intent)
        }
        view.little_recyclerview.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
            override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {

                when(e?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = e?.x
                        startY = e?.y
                    }
                }
                currentX = e?.x
                currentY = e?.y

                var gapX = currentX!! - startX!!
                var gapY = currentY!! - startY!!

                if(abs(gapX) > abs(gapY)) {
                    Log.d(TAG, "HORIZONTAL")
                } else {
                    Log.d(TAG, "VERTICAL")
                }

                Log.d(TAG, "" + abs(gapX) + ", " + abs(gapY))

                return false

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })

    }

}