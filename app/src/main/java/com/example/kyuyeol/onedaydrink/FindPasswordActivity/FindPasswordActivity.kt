package com.example.kyuyeol.onedaydrink.FindPasswordActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tsengvn.typekit.TypekitContextWrapper

class FindPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}