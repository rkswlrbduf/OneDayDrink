package com.example.kyuyeol.onedaydrink.SplashActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import com.example.kyuyeol.onedaydrink.LoginActivity.LoginActivity
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.R
import com.example.kyuyeol.onedaydrink.SignInActivity.SignInActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.ApiErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.activity_splash1.*

class SplashActivity : Activity(), View.OnClickListener {

    val TAG = "SplashActivity_LOG"

    val callback: SplashActivity.SessionCallback = SessionCallback()

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.kakao_login -> kakao_login.performClick()
            R.id.custom_login -> {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
            }
            R.id.custom_signin -> {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)

        Session.getCurrentSession().addCallback(callback)

        custom_login.setOnClickListener(this)
        custom_signin.setOnClickListener(this)
        kakao_login.setOnClickListener(this)

        Handler().postDelayed({
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                redirectToLoginActivity()
            } else {
                redirectToLoginActivity()
            }
        }, 1000)

    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun redirectToLoginActivity() {
        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(splash_constraint)
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(this, R.layout.activity_splash2)

        TransitionManager.beginDelayedTransition(splash_constraint)
        constraintSet2.applyTo(splash_constraint)
    }

    private fun redirectMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    protected fun requestMe() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
                Log.d(TAG, message)

                val result = errorResult.errorCode
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    finish()
                } else {
                    redirectToLoginActivity()
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                redirectToLoginActivity()
            }

            override fun onSuccess(result: MeV2Response) {
                if (result.hasSignedUp() == OptionalBoolean.FALSE) {

                } else {
                    redirectMainActivity()
                }
            }
        })
    }

    inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            requestMe()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.e(TAG, exception.message)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

}