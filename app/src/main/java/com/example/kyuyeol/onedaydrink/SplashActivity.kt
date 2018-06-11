package com.example.kyuyeol.onedaydrink

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.SignActivity.SignActivity
import com.google.firebase.auth.FirebaseAuth
import com.kakao.auth.Session
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : Activity() {

    val TAG = "SplashActivity_LOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)

        val currentUser = FirebaseAuth.getInstance().currentUser
/*

        splash.postDelayed({

        }, 500)
*/

        if (currentUser == null && !Session.getCurrentSession().checkAndImplicitOpen()) {
            redirectToLoginActivity()
        }else {
            goToMainActivity()
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this@SplashActivity, SignActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}