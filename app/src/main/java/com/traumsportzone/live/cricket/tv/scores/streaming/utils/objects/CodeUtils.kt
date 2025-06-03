package com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects

import android.os.SystemClock
import android.view.View
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import java.util.ArrayList

object CodeUtils {

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    fun View.visibility(visibility: Boolean) {
        if (visibility) this.visibility=View.VISIBLE else this.visibility=View.GONE
    }
    fun checkNativeAd(list: List<LiveScoresModel?>): List<LiveScoresModel?> {
        val tempChannels: MutableList<LiveScoresModel?> =
            ArrayList()
        for (i in list.indices) {
            val diff = i % 5
            if (diff == 2) {

                tempChannels.add(null)
            }
            tempChannels.add(list[i])
            if (list.size == 2) {
                if (i == 1) {
                    tempChannels.add(null)

                }
            }

        }
        return tempChannels
    }

    class SafeClickListener(

        private var defaultInterval: Int = 1000,
        private val onSafeCLick: (View) -> Unit
    ) : View.OnClickListener {
        private var lastTimeClicked: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
                return
            }
            lastTimeClicked = SystemClock.elapsedRealtime()
            onSafeCLick(v)
        }
    }
}