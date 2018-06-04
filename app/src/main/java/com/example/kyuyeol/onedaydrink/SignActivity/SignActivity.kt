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
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.AuthCredential
import com.facebook.AccessToken
import com.google.android.gms.tasks.OnCompleteListener
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.ApiErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger


class SignActivity : AppCompatActivity() {

    val CLIENT_ID = "127279302599-ujilbih6vd4cqkclqqd2crp0iahusbpc.apps.googleusercontent.com"
    val RC_SIGN_IN = 1001
    val TAG = "SignActivity_Log"

    lateinit var mGoogleSignInAccount: GoogleSignInClient
    lateinit var mAuth: FirebaseAuth
    lateinit var callbackManager: CallbackManager
    private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .build()

        mGoogleSignInAccount = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this) // null이 아니면 다음 액티비티로 전환하는 알고리즘 짜야됨

        google_login.setSize(SignInButton.SIZE_STANDARD)
        google_login.setOnClickListener({ v -> signIn() })

        mAuth = FirebaseAuth.getInstance()

        google_logout.setOnClickListener({ v ->
            mAuth.signOut()
            mGoogleSignInAccount.signOut().addOnCompleteListener(this, { v -> null })
        })

        /*FaceBook Start*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        facebook_login.setReadPermissions("email", "public_profile")

        // Callback registration
        facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "FACEBOOK WORK : " + loginResult.accessToken)
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            override fun onCancel() {
                Log.d(TAG, "FACEBOOK CANCEL")
            }

            override fun onError(exception: FacebookException) {
                Log.w(TAG, exception.message)
            }
        })

        val accessToken = AccessToken.getCurrentAccessToken();
        val isLoggedIn = accessToken != null && !accessToken.isExpired();

        facebook_logout.setOnClickListener({ v ->
            mAuth.signOut()
        })

        /*FaceBook End*/


        /**
         * 카카오톡
         */
        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)

        kakao_logout.setOnClickListener({ v ->
            UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                override fun onCompleteLogout() {
                    redirectLoginActivity()
                }
            })
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
    }

    fun signIn() {
        val signInIntent = mGoogleSignInAccount.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
            Toast.makeText(this, "Email : " + account.email, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "GOOGLE WORK")
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        /**
         * 구글
         */
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        /**
         * 페이스북
         */
        callbackManager.onActivityResult(requestCode, resultCode, data)

        /**
         * 카카오톡
         */
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)

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

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener({ task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    /** 카카오톡 로그인 */

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

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            Log.d(TAG, "Kakao Session Opened")
            requestMe()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.e(TAG, exception.message + "   ㄴㅇㄴ")
            }
        }
    }

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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}