package com.example.kyuyeol.onedaydrink.StoreActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.StoreActivity.Adapter.StoreMenuRecyclerViewAdapter
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.activity_store.*

class StoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        store_menu.layoutManager = GridLayoutManager(this, 2)
        store_menu.adapter = StoreMenuRecyclerViewAdapter(this)



    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}