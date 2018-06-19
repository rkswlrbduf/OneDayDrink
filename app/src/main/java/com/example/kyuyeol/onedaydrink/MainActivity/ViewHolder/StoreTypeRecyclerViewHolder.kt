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

class StoreTypeRecyclerViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {

    var storeList: ArrayList<StoreInform> = arrayListOf(StoreInform("한판 닭갈비", "10분", ", ★ 4.5"),
            StoreInform("왕코 등갈비", "5분", ", ★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"),
            StoreInform("A", "3", "★ 3.5"))

    val intent = Intent(context, StoreActivity::class.java)
    val TAG = "StoreTypeRecycler_Log"
    var mIsScrolling = false

    var downX : Float = 0f
    var downY : Float = 0f

    fun bind(list: ArrayList<String>) {
        view.little_title.setText(list[adapterPosition])
        view.little_recyclerview.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        view.little_recyclerview.adapter = StoreRecyclerViewAdapter(context, storeList) { storeInform ->
            context.startActivity(intent)
        }
        view.little_recyclerview.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView?, ev: MotionEvent): Boolean {

                val action = MotionEventCompat.getActionMasked(ev)

                if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                    mIsScrolling = false
                    return false
                }

                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        mIsScrolling = false
                        downX = ev.x
                        downY = ev.y
                        //Log.d(TAG, "ACTION_DOWN")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (mIsScrolling) {
                            return true
                        }

                        var xDiff = calculateDistanceX(downX, ev.x)
                        var yDiff = calculateDistanceY(downY, ev.y)

                        Log.d(TAG, "xDiff : " + xDiff + ", yDiff : " + yDiff)

                        if (yDiff > xDiff) {
                            Log.d(TAG, "VERTICAL")
                            mIsScrolling = true
                            return true
                        }
                    }
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
        /*view.little_recyclerview.setOnTouchListener(View.OnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true);
            return@OnTouchListener false
        })*/
    }

    fun setOriginalMotionEvent(ev : MotionEvent){
    }

    fun calculateDistanceX(downX : Float, upX : Float) : Float {
        return Math.abs(downX - upX)
    }

    fun calculateDistanceY(downY : Float, upY : Float) : Float {
        return Math.abs(downY - upY)
    }

}