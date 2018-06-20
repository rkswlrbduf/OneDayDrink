package com.example.kyuyeol.onedaydrink.BookMarkActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.kyuyeol.onedaydrink.R
import kotlinx.android.synthetic.main.activity_bookmark.*

class BookMarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        bookmark_recyclerview.adapter = BookMarkRecyclerViewAdapter(this)
        bookmark_recyclerview.layoutManager = LinearLayoutManager(this)

    }

}