package com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces

import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel

interface ObserveLiveScore {
    fun scoresData(liveModel: List<LiveScoresModel>)
}