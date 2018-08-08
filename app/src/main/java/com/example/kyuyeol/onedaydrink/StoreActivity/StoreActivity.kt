package com.example.kyuyeol.onedaydrink.StoreActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.Adapter.StoreAdapter
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.StoreImageMenuInform
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.StoreInform
import com.example.kyuyeol.onedaydrink.StoreActivity.StoreClass.StoreTextMenuInform
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.store_image_menu.view.*

class StoreActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        var storeImageMenuList = ArrayList<StoreImageMenuInform>()
        storeImageMenuList.add(StoreImageMenuInform("3", "한성포차", "10,000원"))
        storeImageMenuList.add(StoreImageMenuInform("4", "한성포차", "10,000원"))
        storeImageMenuList.add(StoreImageMenuInform("5", "한성포차", "10,000원"))
        storeImageMenuList.add(StoreImageMenuInform("6", "한성포차", "10,000원"))

        var storeTextMenuInform = ArrayList<StoreTextMenuInform>()
        storeTextMenuInform.add(StoreTextMenuInform("한성포차1", "10,000원"))
        storeTextMenuInform.add(StoreTextMenuInform("한성포차2", "10,000원"))
        storeTextMenuInform.add(StoreTextMenuInform("한성포차3", "10,000원"))
        storeTextMenuInform.add(StoreTextMenuInform("한성포차4", "10,000원"))
        storeTextMenuInform.add(StoreTextMenuInform("한성포차5", "10,000원"))
        storeTextMenuInform.add(StoreTextMenuInform("한성포차6", "10,000원"))

        var storeInform = StoreInform("1",
                "한성포차",
                "곱창과 대창, 염통, 막창, 특양구이는 하루에 한가지 맛씩 각각의 맛을 오롯이 즐기고자 하는 분들은 단품으로도 즐길 수 있습니다. 하지만 다양한 맛을 한 번에 즐기고 싶은 분들을 위해 모둠메뉴도 다양하게 구성이 되어 있습니다.",
                "서울특별시 용산구 청파동 2가",
                "02-333-3333",
                "평일 00시00분 - 22시22분",
                "주말 00시00분 - 00시00분",
                storeImageMenuList,
                storeTextMenuInform,
                "Map"
        )

        Glide.with(this).load("http://stou2.cafe24.com/image/" + storeInform.store_image + ".jpg").apply(RequestOptions().centerCrop()).into(store_image)
        store_name.text = storeInform.store_name
        store_intro.text = storeInform.store_intro
        store_address.text = storeInform.store_address
        store_call.text = storeInform.store_call
        store_day_time.text = storeInform.store_day_time
        store_end_time.text = storeInform.store_end_time

        Glide.with(this).load("http://stou2.cafe24.com/image/" + storeInform.store_image_menu_list[0].store_image_menu_image + ".jpg").apply(RequestOptions().centerCrop()).into(store_image_menu_1.store_menu_image)
        store_image_menu_1.store_menu_name.text = storeInform.store_image_menu_list[0].store_image_menu_name
        store_image_menu_1.store_menu_price.text = storeInform.store_image_menu_list[0].store_image_menu_price

        Glide.with(this).load("http://stou2.cafe24.com/image/" + storeInform.store_image_menu_list[1].store_image_menu_image + ".jpg").apply(RequestOptions().centerCrop()).into(store_image_menu_2.store_menu_image)
        store_image_menu_2.store_menu_name.text = storeInform.store_image_menu_list[1].store_image_menu_name
        store_image_menu_2.store_menu_price.text = storeInform.store_image_menu_list[1].store_image_menu_price

        Glide.with(this).load("http://stou2.cafe24.com/image/" + storeInform.store_image_menu_list[2].store_image_menu_image + ".jpg").apply(RequestOptions().centerCrop()).into(store_image_menu_3.store_menu_image)
        store_image_menu_3.store_menu_name.text = storeInform.store_image_menu_list[2].store_image_menu_name
        store_image_menu_3.store_menu_price.text = storeInform.store_image_menu_list[2].store_image_menu_price

        Glide.with(this).load("http://stou2.cafe24.com/image/" + storeInform.store_image_menu_list[3].store_image_menu_image + ".jpg").apply(RequestOptions().centerCrop()).into(store_image_menu_4.store_menu_image)
        store_image_menu_4.store_menu_name.text = storeInform.store_image_menu_list[3].store_image_menu_name
        store_image_menu_4.store_menu_price.text = storeInform.store_image_menu_list[3].store_image_menu_price

        store_menu_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        store_menu_recyclerview.adapter = StoreAdapter(this, storeInform.store_text_menu_list)

    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}