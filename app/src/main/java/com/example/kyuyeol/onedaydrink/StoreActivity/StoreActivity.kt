package com.example.kyuyeol.onedaydrink.StoreActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.Adapter.StoreDrinkRecyclerViewAdapter
import com.example.kyuyeol.onedaydrink.StoreActivity.Adapter.StoreMenuRecyclerViewAdapter
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.DrinkInform
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.MenuInform
import com.skt.Tmap.TMapView
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.activity_store.*

class StoreActivity : AppCompatActivity() {

    val menuList = arrayListOf<MenuInform>(MenuInform("감자 튀김", "5000원"),
            MenuInform("감자 주먹", "3000원"),
            MenuInform("감자 가위", "4000원"),
            MenuInform("감자 보자기", "6000원"),
            MenuInform("감자 머리", "33000원"))

    val drinkList = arrayListOf<DrinkInform>(DrinkInform("소주"),
            DrinkInform("맥주"),
            DrinkInform("자몽에이슬"),
            DrinkInform("아침에이슬"),
            DrinkInform("저녁에이슬"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val tmapview = TMapView(this)
        tmapview.setSKTMapApiKey("df15431c-c688-49f4-b53a-6e5f56f0ed90")
        store_map.addView(tmapview)

        store_menu.layoutManager = GridLayoutManager(this, 2)
        store_menu.adapter = StoreMenuRecyclerViewAdapter(this, menuList)

        store_drink.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        store_drink.adapter = StoreDrinkRecyclerViewAdapter(this, drinkList)

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}