package com.example.kyuyeol.onedaydrink

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tab1.*

class Tab1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View? = inflater.inflate(R.layout.fragment_tab1, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var barArrayData = ArrayList<BarData>()

        var barData : BarData = BarData("store_name1", "store_detail_place1", "store_distance1",
                "android_logo", "store_place1", "store_service1")
        barArrayData.add(barData)
        barData = BarData("store_name2", "store_detail_place2", "store_distance2",
                "android_logo", "store_place2", "store_service2")
        barArrayData.add(barData)
        barData = BarData("store_name3", "store_detail_place3", "store_distance3",
                "android_logo", "store_place3", "store_service3")
        barArrayData.add(barData)
        barData = BarData("store_name4", "store_detail_place4", "store_distance4",
                "android_logo", "store_place4", "store_service4")
        barArrayData.add(barData)

        val adapter  = RecyclerviewAdapter(context, barArrayData)
        fragment1_recyclerview.adapter = adapter
        fragment1_recyclerview.layoutManager = LinearLayoutManager(context)

    }

}