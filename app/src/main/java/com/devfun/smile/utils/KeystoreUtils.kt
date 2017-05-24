package com.devfun.smile.utils

import android.content.Context
import android.security.KeyPairGeneratorSpec
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.*
import javax.security.auth.x500.X500Principal

/**
 * *******************************************
 * * Created by Simon on 5/24/2017.         **
 * * Copyright (c) 2017 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */
class KeystoreUtils {

    init {

    }

    private object Holder {
        val INSTANCE = KeystoreUtils()
    }

    companion object {
        val instance: KeystoreUtils by lazy { Holder.INSTANCE }
    }

    fun createStoreKey(context: Context, alias: String) {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null)
            if (!keyStore.containsAlias(alias)) {
                val start = Calendar.getInstance()
                val end = Calendar.getInstance()
                end.add(Calendar.YEAR, 100)
                val spec = KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()
                val generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")
                generator.initialize(spec);
                val keyPair = generator.generateKeyPair()
            }
        } catch (e: Exception) {

        }
    }
}