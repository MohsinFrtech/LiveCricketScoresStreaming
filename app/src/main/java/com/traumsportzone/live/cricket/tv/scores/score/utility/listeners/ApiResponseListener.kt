package com.traumsportzone.live.cricket.tv.scores.score.utility.listeners

interface ApiResponseListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}