package com.devfun.smile

import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * *******************************************
 * * Project android-kotlin-firebase        **
 * * Created by Simon on 6/12/2017.         **
 * * Copyright (c) 2017 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService(){

    override fun onTokenRefresh() {
        super.onTokenRefresh()
    }
}