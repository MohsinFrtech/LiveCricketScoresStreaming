package com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces

import com.futgenix.soccer.scores.models.FormatDataAudioMedia3

////This interface is for controlling navigation between fragments
interface FormatSelectionAudioMedia3 {
    fun navigation(viewId: FormatDataAudioMedia3, pos:Int, countryCode:String?)
}