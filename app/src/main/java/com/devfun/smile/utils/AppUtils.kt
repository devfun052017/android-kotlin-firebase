package com.devfun.smile.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

/**
 * *******************************************
 * * Created by Simon on 5/19/2017.         **
 * * Copyright (c) 2017 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */
class AppUtils private constructor() {
    val mRefreshData: String = "com.devfun.funnyjokes.REFRESH_DATA"
    init {

    }

    private object Holder {
        val INSTANCE = AppUtils()
    }

    companion object {
        val instance: AppUtils by lazy { Holder.INSTANCE }
    }

    fun sendMail(context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("devfun052017@gmail.com"));
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Smile Feedback!");
        context.startActivity(Intent.createChooser(intent, "Send mail..."));
    }

    fun printHashKey(context: Context) {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName,
                    PackageManager.GET_SIGNATURES);
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun rating(context: Context) {
        val uri = Uri.parse(String.format("market://details?id=%s", context.packageName))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (ex: Exception) {
            val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(
                    String.format("http://play.google.com/store/apps/details?id=%s",
                            context.packageName)))
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent1)
        }
    }
}
