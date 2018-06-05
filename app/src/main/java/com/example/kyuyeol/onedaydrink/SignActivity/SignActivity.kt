package com.example.kyuyeol.onedaydrink.SignActivity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kyuyeol.onedaydrink.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_sign.*
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.tsengvn.typekit.TypekitContextWrapper
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.auth.GoogleAuthProvider
import com.facebook.login.LoginResult
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.google.firebase.auth.FacebookAuthProvider
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.ApiErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import java.util.*


class SignActivity : AppCompatActivity() {

    val CLIENT_ID = "127279302599-ujilbih6vd4cqkclqqd2crp0iahusbpc.apps.googleusercontent.com"
    val RC_SIGN_IN = 1001
    val TAG = "SignActivity_Log"
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val callbackManager: CallbackManager = CallbackManager.Factory.create()
    val callback: SessionCallback = SessionCallback()

    lateinit var mGoogleSignInAccount: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        /**
         * 구글
         */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .build()

        mGoogleSignInAccount = GoogleSignIn.getClient(this, gso)

        google_login.setSize(SignInButton.SIZE_STANDARD)
        google_login.setOnClickListener({ v -> signIn() })

        /**
         * 페이스북
         */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        facebook_login.setReadPermissions(Arrays.asList("user_status", "public_profile", "email"))
        facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "handleFacebook")
                val credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().token)
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                redirectMainActivity()
                                Log.d(TAG, "REDIRECT+")
                                val user = mAuth.currentUser
                            } else {

                            }
                        })
            }

            override fun onCancel() {
                Log.d(TAG, "FACEBOOK CANCEL")
            }

            override fun onError(exception: FacebookException) {
                Log.w(TAG, exception.message)
            }
        })

        /**
         * 카카오톡
         */
        Session.getCurrentSession().addCallback(callback)

    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    /**
     * 구글
     */

    fun signIn() {
        val signInIntent = mGoogleSignInAccount.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
            redirectMainActivity()
            Toast.makeText(this, "Email : " + account.email, Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode())
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener({ task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser
                        //updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                })
    }

    /**
     * 카카오톡
     */

    protected fun requestMe() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
                Log.d(TAG, message)

                val result = errorResult.errorCode
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    finish()
                } else {
                    redirectLoginActivity()
                    Log.d(TAG, "NUM1")
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                redirectLoginActivity()
                Log.d(TAG, "NUM2" + errorResult.errorMessage)
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

    /**
     * 리다이렉트
     */

    private fun redirectMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Log.d(TAG, "redirectMainActivity")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun redirectLoginActivity() {
        val intent = Intent(this, SignActivity::class.java)
        Log.d(TAG, "redirectLoginActivity")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    /**
     * 폰트 설정
     */

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    /**
     * onActivityResult
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        /**
         * 카카오톡
         */
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        /**
         * 구글
         */
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            /**
             * 페이스북
             */
            callbackManager.onActivityResult(requestCode, resultCode, data)

        }

        super.onActivityResult(requestCode, resultCode, data)

    }

}