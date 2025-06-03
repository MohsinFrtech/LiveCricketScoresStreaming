package com.traumsportzone.live.cricket.tv.scores.score.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

// Most Wickets
data class MainMostWicket (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : List<MostWickets> ? = null
)
data class MostWickets (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Bowler"    ) var Bowler   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("O"         ) var O        : String? = null,
    @SerializedName("W"         ) var W        : String? = null,
    @SerializedName("Avg"       ) var Avg      : String? = null
)


// Most Runs
data class MainMostRuns (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostRuns> = arrayListOf()
)
data class MostRuns (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("Avg"       ) var Avg      : String? = null
)


// Highest Score
data class MainHighestScore (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<HighestScore> = arrayListOf()
)
data class HighestScore (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("HS"        ) var HS       : String? = null,
    @SerializedName("Balls"     ) var Balls    : String? = null,
    @SerializedName("SR"        ) var SR       : String? = null,
    @SerializedName("Vs"        ) var Vs       : String? = null
)


// Highest Strike Rate
data class MainHighestStrrikeRate (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<HighestStrrikeRate> = arrayListOf()
)
data class HighestStrrikeRate (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("SR"        ) var SR       : String? = null
)


// Most Hundereds
data class MainMostHundereds (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostHundereds> = arrayListOf()
)
data class MostHundereds (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("100s"      ) var hundered     : String? = null
)


// Most Fifties
data class MainMostFifties (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostFifties> = arrayListOf()
)
data class MostFifties (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("50s"       ) var fifty      : String? = null
)


// Most Six
data class MainMostSix (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostSix> = arrayListOf()
)
data class MostSix (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("6s"        ) var sixes       : String? = null
)


// Most Four
data class MianMostFour (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostFour> = arrayListOf()
)
data class MostFour (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("4s"        ) var fours       : String? = null
)


// Highest Average
data class MainHighestAverage (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<HighestAverage> = arrayListOf()
)
data class HighestAverage (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("Avg"       ) var Avg      : String? = null
)


// Most Ninties
data class MainMostNinties (
    @SerializedName("stat_type"  ) var statType  : String?            = null,
    @SerializedName("match_type" ) var matchType : String?            = null,
    @SerializedName("tag"        ) var tag       : String?            = null,
    @SerializedName("records"    ) var records   : ArrayList<MostNinties> = arrayListOf()
)
data class MostNinties (
    @SerializedName("player_id" ) var playerId : String? = null,
    @SerializedName("Batter"    ) var Batter   : String? = null,
    @SerializedName("M"         ) var M        : String? = null,
    @SerializedName("I"         ) var I        : String? = null,
    @SerializedName("R"         ) var R        : String? = null,
    @SerializedName("90s"       ) var ninty      : String? = null
)

//@Parcelize
//@JsonClass(generateAdapter = true)
//data class MainMostWicket(
//    var stat_type : String? = null,
//    var match_type : String? = null,
//    var tag : String? = null,
//    @Json(name = "records") var records : List<MostWickets>
//) : Parcelable
//
//@Parcelize
//@JsonClass(generateAdapter = true)
//data class MostWickets(
//    var player_id : Int,
//    var Bowler : String,
//    var M : Int,
//    var O : DecimalFormat,
//    var W : Int,
//    var Avg : DecimalFormat
//) : Parcelable
//
//@Parcelize
//@JsonClass(generateAdapter = true)
//data class MainMostRun(
//    var stat_type : String? = null,
//    var match_type : String? = null,
//    var tag : String? = null,
//    @Json(name = "records") var records : List<MostRun>
//) : Parcelable
//
//@Parcelize
//@JsonClass(generateAdapter = true)
//data class MostRun(
//    var player_id: Int,
//    var Batter : Int,
//    var M : Int,
//    var
//) : Parcelable