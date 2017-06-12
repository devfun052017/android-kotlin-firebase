package com.devfun.smile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ShowFullAdsActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(R.drawable.transparent)
        mInterstitialAd = InterstitialAd(applicationContext)
        if (mInterstitialAd == null) {
            finish()
            return
        }
        mInterstitialAd!!.adUnitId = getString(R.string.ad_interstitial_id)
        mInterstitialAd!!.loadAd(AdRequest.Builder().addTestDevice("C96AB204E75D32E0C479250F6DDC97E2").build())
        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                mInterstitialAd!!.show()
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                finish()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                finish()
            }
        }
    }

}
