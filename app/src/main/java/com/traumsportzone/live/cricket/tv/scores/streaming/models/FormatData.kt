package com.traumsportzone.live.cricket.tv.scores.streaming.models

import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.Tracks


data class FormatData(var token: Format?=null,
                      var trckGroup: Tracks.Group?=null,
                      var checkUncheck:Boolean=false
)