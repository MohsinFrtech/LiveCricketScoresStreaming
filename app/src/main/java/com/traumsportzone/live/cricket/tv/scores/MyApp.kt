package com.traumsportzone.live.cricket.tv.scores

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.facebook.ads.AudienceNetworkAds
/*import com.p2pengine.core.p2p.P2pConfig
import com.p2pengine.core.utils.AnnounceLocation
import com.p2pengine.sdk.P2pEngine*/       // Remove by Haris Abbas (p2p)
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AppOpenManager

class MyApp : Application() {

    private var appOpenManager: AppOpenManager? = null
    override fun onCreate() {
        super.onCreate()
        appOpenManager = AppOpenManager(this)
        AudienceNetworkAds.initialize(this)

        /*try {
            Handler(Looper.getMainLooper()).post {
                initP2pSdk()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/     // Remove by Haris Abbas (p2p)


    }

    /*private fun initP2pSdk() {
        val config = P2pConfig.Builder()
            .p2pEnabled(true)
//            .logEnabled(true)
//            .logLevel(LogLevel.DEBUG)
            .announceLocation(AnnounceLocation.USA)
//            .announceLocation(AnnounceLocation.HongKong)
//            .announceLocation(AnnounceLocation.USA)
            .build()

        //println("MainActivity P2pEngine init")
        P2pEngine.init(applicationContext, "WUAlHM1Vg", config)

    }*/     // Remove by Haris Abbas (p2p)
}