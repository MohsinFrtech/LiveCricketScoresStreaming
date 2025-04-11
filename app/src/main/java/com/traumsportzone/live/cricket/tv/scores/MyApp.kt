package com.traumsportzone.live.cricket.tv.scores

import android.app.Application
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AppOpenManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.AppContextProvider
import java.util.Arrays

class MyApp : Application() {

    private var appOpenManager: AppOpenManager? = null
    override fun onCreate() {
        super.onCreate()
        appOpenManager = AppOpenManager(this)
        AudienceNetworkAds.initialize(this)
        AppContextProvider.init(this)
//        val testDeviceIds = Arrays.asList("890CE0083E0D133594E4E763481D1140")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)
    }

}