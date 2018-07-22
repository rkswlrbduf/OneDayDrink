package com.example.kyuyeol.onedaydrink.SignInActivity

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
import android.widget.CompoundButton
import android.widget.Toast
import com.example.kyuyeol.onedaydrink.MainActivity.MainActivity
import com.example.kyuyeol.onedaydrink.SplashActivity.SplashActivity
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kakao.usermgmt.StringSet.email
import com.tsengvn.typekit.TypekitContextWrapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_signin.*
import java.util.concurrent.TimeUnit


class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private var isInputCorrectData: Array<Boolean> = arrayOf(false, false, false, false, false, false)

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
            R.id.signin_close -> finish()
            R.id.signin_signin -> {
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                finish()
                overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signin_close.setOnClickListener(this)

        signin_signin.isEnabled = false
        signin_signin.setBackgroundResource(R.drawable.button_state)

        var passwordObservable: Observable<CharSequence> = RxTextView.textChanges(signin_password_text)
        var confirmPasswordObservable: Observable<CharSequence> = RxTextView.textChanges(signin_password_confirm_text)

        RxTextView.afterTextChangeEvents(signin_name_text)
                .skipInitialValue()
                .map {
                    signin_name_layout.error = null
                    isInputCorrectData[0] = true
                    it.view().text.toString()
                }
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    signin_name_layout.error = it.message
                    isInputCorrectData[0] = false
                    signin_signin.isEnabled = false
                })
                .subscribe({ reactiveCheckCorrectData() })

        RxTextView.afterTextChangeEvents(signin_email_text)
                .skipInitialValue()
                .map {
                    signin_email_layout.error = null
                    isInputCorrectData[1] = true
                    it.view().text.toString()
                }
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(verifyEmailPattern)
                .compose(retryWhenError {
                    signin_email_layout.error = it.message
                    isInputCorrectData[1] = false
                    signin_signin.isEnabled = false
                })
                .subscribe({ reactiveCheckCorrectData() })

        RxTextView.afterTextChangeEvents(signin_password_text)
                .skipInitialValue()
                .map {
                    signin_password_layout.error = null
                    isInputCorrectData[2] = true
                    it.view().text.toString()
                }
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    signin_password_layout.error = it.message
                    isInputCorrectData[2] = false
                    signin_signin.isEnabled = false
                })
                .subscribe({ reactiveCheckCorrectData() })

        RxTextView.afterTextChangeEvents(signin_password_confirm_text)
                .skipInitialValue()
                .map {
                    signin_password_confirm_layout.error = null
                    isInputCorrectData[3] = true
                    it.view().text.toString()
                }
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    signin_password_confirm_layout.error = it.message
                    isInputCorrectData[3] = false
                    signin_signin.isEnabled = false
                })
                .subscribe({ reactiveCheckCorrectData() })

        Observable.combineLatest(passwordObservable, confirmPasswordObservable,
                object : BiFunction<CharSequence, CharSequence, Boolean> {
                    override fun apply(t1: CharSequence, t2: CharSequence): Boolean {
                        var password = t1.toString()
                        var confirmPassword = t2.toString()
                        return !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword)
                    }
                })
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(object : Observer<Boolean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Boolean) {
                        isInputCorrectData[4] = t
                        if (!t) {
                            signin_password_confirm_layout.error = "비밀번호가 일치하지 않습니다."
                            signin_signin.isEnabled = false
                        } else {
                            signin_password_confirm_layout.error = null
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })

        signin_rule_check.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                isInputCorrectData[5] = isChecked
                reactiveCheckCorrectData()
            }
        })

        signin_signin.setOnClickListener(this)

    }

    private fun reactiveCheckCorrectData() {
        for (check in isInputCorrectData) {
            if (!check) {
                return
            }
        }
        signin_signin.isEnabled = true
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}