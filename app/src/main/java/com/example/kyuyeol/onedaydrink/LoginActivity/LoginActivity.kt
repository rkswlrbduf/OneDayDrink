package com.example.kyuyeol.onedaydrink.LoginActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.kyuyeol.onedaydrink.R
import kotlinx.android.synthetic.main.activity_login.*
import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.SplashActivity.SplashActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kakao.usermgmt.StringSet.email
import com.tsengvn.typekit.TypekitContextWrapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var isInputCorrectData: Array<Boolean> = arrayOf(false, false)

    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> = ObservableTransformer { observable ->
        observable.retryWhen { errors ->
            errors.flatMap {
                onError(it)
                Observable.just("")
            }
        }
    }

    private val lengthGreaterThanSix = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() }
                    .filter { it.length > 6 }
                    .singleOrError()
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.error(Exception("6자리 이상으로 입력해주세요."))
                        } else {
                            Single.error(it)
                        }
                    }
                    .toObservable()
        }
    }

    private val verifyEmailPattern = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() }
                    .filter {
                        Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    }
                    .singleOrError()
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.error(Exception("유효하지 않은 이메일 형식입니다."))
                        } else {
                            Single.error(it)
                        }
                    }
                    .toObservable()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_close -> finish()
            R.id.login_login -> {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
                overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_close.setOnClickListener(this)

        login_login.isEnabled = false
        login_login.setBackgroundResource(R.drawable.button_state)

        Log.d(this@LoginActivity.toString(), lengthGreaterThanSix.toString())

        val emailChangeObservable = RxTextView.afterTextChangeEvents(login_email_text)
                .skipInitialValue()
                .map {
                    login_email_layout.error = null
                    isInputCorrectData[0] = true
                    it.view().text.toString()
                }
                .debounce(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(verifyEmailPattern)
                .compose(retryWhenError {
                    login_email_layout.error = it.message
                    isInputCorrectData[0] = false
                    login_login.isEnabled = false
                })
                .subscribe({reactiveCheckCorrectData()})

        val passwordChangeObservable = RxTextView.afterTextChangeEvents(login_password_text)
                .skipInitialValue()
                .map {
                    login_password_layout.error = null
                    isInputCorrectData[1] = true
                    it.view().text.toString()
                }
                .debounce(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    login_password_layout.error = it.message
                    isInputCorrectData[1] = false
                    login_login.isEnabled = false
                })
                .subscribe({reactiveCheckCorrectData()})

        login_login.setOnClickListener(this)

    }

    private fun reactiveCheckCorrectData() {
        for (check in isInputCorrectData) {
            if (!check) {
                return
            }
        }
        login_login.isEnabled = true
        Log.d(this@LoginActivity.toString(), ""+ login_login.isEnabled)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}