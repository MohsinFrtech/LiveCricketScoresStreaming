package com.futgenix.soccer.scores.models

import androidx.media3.common.Format
import androidx.media3.common.Tracks


data class FormatDataMedia3(var token: Format?=null,
                            var trckGroup: Tracks.Group?=null,
                            var checkUncheck:Boolean=false
)