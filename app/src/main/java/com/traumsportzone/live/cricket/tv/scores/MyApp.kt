package com.traumsportzone.live.cricket.tv.scores

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.facebook.ads.AudienceNetworkAds
import com.p2pengine.core.p2p.EngineExceptionListener
import com.p2pengine.core.p2p.P2pConfig
import com.p2pengine.core.segment.StrictHlsSegmentIdGenerator
import com.p2pengine.core.tracking.TrackerZone
import com.p2pengine.core.utils.EngineException
import com.p2pengine.sdk.P2pEngine
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

        try {
            val config = P2pConfig.Builder()
                .p2pEnabled(true)
                //.logEnabled(true)
                //.logLevel(LogLevel.DEBUG)
                .trackerZone(TrackerZone.Europe)
                .insertTimeOffsetTag(0.0)
                .build()
            P2pEngine.init(applicationContext, "WUAlHM1Vg", config)
//            P2pEngine.getInstance()?.setHlsSegmentIdGenerator(StrictHlsSegmentIdGenerator())

            P2pEngine.getInstance()?.registerExceptionListener(object : EngineExceptionListener {
                override fun onOtherException(e: EngineException) {

                }

                override fun onSchedulerException(e: EngineException) {

                }

                override fun onSignalException(e: EngineException) {

                }

                override fun onTrackerException(e: EngineException) {

                }


            })

        } catch (e: Exception) {
            Log.d("Exception", "msg")
        }


    }

}