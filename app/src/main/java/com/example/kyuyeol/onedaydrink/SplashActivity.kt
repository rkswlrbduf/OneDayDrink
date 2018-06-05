package com.example.kyuyeol.onedaydrink

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.SignActivity.SignActivity
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : Activity() {

    lateinit var callback : ISessionCallback
    val TAG = "SplashActivity_LOG"


    val accessToken: AccessToken? = AccessToken.getCurrentAccessToken() // facebook
    val isLoggedIn = accessToken != null && !accessToken!!.isExpired // facebook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        /**
         * 카카오톡
         */
/*        callback = object : ISessionCallback {
            override fun onSessionOpened() {
                goToMainActivity()
            }

            override fun onSessionOpenFailed(exception: KakaoException?) {
                redirectToLoginActivity()
            }
        }*/

//        Session.getCurrentSession().addCallback(callback)
        /*splash.postDelayed({
            if (!Session.getCurrentSession().checkAndImplicitOpen() && account == null && !isLoggedIn) {
                redirectToLoginActivity()
            }
        }, 500)*/

    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null && !Session.getCurrentSession().checkAndImplicitOpen()) redirectToLoginActivity()
        else goToMainActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Session.getCurrentSession().removeCallback(callback)
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