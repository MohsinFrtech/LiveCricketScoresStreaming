package com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces

import com.traumsportzone.live.cricket.tv.scores.streaming.models.FormatData


////This interface is for controlling navigation between fragments
interface FormatSelection {
    fun navigation(viewId: FormatData, pos:Int)
}