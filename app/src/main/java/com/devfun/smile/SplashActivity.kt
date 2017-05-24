package com.devfun.smile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        FirebaseAuth.getInstance()
                .signInAnonymously()
                .addOnCompleteListener(this) { p0 ->
                    Log.d("auth", String.format("%s", p0.isSuccessful))
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
    }
}
