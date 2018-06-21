package com.example.kyuyeol.onedaydrink.ContactActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kyuyeol.onedaydrink.R
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        kakao_friend_add.setOnClickListener({ v ->
            val url = "kakaoplus://plusfriend/home/@%EC%88%A0%EA%B9%83%ED%95%9C%EC%A0%9C%EC%95%88"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        })

    }
}