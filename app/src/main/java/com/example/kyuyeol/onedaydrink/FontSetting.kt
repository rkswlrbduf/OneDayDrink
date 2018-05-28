package com.example.kyuyeol.onedaydrink

import android.app.Application
import com.tsengvn.typekit.Typekit

class FontSetting : Application() {

    override fun onCreate() {
        super.onCreate()

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumSquareR.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumSquareB.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "NanumSquareEB.ttf"))
                .addCustom2(Typekit.createFromAsset(this, "NanumSquareL.ttf"))

    }
}