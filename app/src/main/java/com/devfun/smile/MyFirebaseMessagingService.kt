package com.devfun.smile

import android.content.Intent
import com.devfun.smile.utils.AppUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * *******************************************
 * * Project android-kotlin-firebase        **
 * * Created by Simon on 6/12/2017.         **
 * * Copyright (c) 2017 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        sendBroadcast(Intent(AppUtils.instance.mRefreshData))
    }
}