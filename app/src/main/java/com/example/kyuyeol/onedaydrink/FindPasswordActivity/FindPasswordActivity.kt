package com.example.kyuyeol.onedaydrink.FindPasswordActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.kyuyeol.onedaydrink.R
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.activity_findpassword.*

class FindPasswordActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.find_close -> {
                finish()
            }
            R.id.find_find -> {
                /**
                 * Do Something
                 */
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpassword)

        find_close.setOnClickListener(this)
        find_find.setOnClickListener(this)

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}