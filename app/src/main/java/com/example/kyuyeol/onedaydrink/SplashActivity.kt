package com.example.kyuyeol.onedaydrink

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.SignActivity.SignActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : Activity() {

    lateinit var callback : ISessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        callback = object : ISessionCallback {
            override fun onSessionOpened() {
                goToMainActivity()
            }

            override fun onSessionOpenFailed(exception: KakaoException?) {
                redirectToLoginActivity()
            }
        }

        Session.getCurrentSession().addCallback(callback)
        splash.postDelayed({
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                redirectToLoginActivity()
            }
        }, 500)

    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this@SplashActivity, SignActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}