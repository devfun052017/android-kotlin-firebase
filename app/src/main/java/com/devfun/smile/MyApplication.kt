package com.devfun.smile

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp

/**
 * *******************************************
 * * Created by Simon on 5/8/17.            **
 * * Copyright (c) 2015 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this, "ca-app-pub-9086867599454362~8603560139")
    }


}
