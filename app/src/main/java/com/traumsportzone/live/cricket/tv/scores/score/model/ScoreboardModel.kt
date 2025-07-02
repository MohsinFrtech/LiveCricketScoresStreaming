package com.traumsportzone.live.cricket.tv.scores.score.model

import com.google.gson.annotations.SerializedName

class ScoreboardModel(
    @SerializedName("scorecard") var scoreCard: ArrayList<ScoreCardMatch> = arrayListOf(),
    @SerializedName("matchHeader") var matchHeader: MatchHeader? = MatchHeader(),
    @SerializedName("isMatchComplete") var isMatchComplete: Boolean? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("videos") var videos: ArrayList<String> = arrayListOf(),
    @SerializedName("responseLastUpdated") var responseLastUpdated: Int? = null
)

data class ScoreCardMatch(

    @SerializedName("matchId") var matchId: Long? = null,
    @SerializedName("timeScore") var timeScore: Long? = null,
    @SerializedName("bowlTeamDetails") var bowlTeamDetails: BowlTeamDetails? = BowlTeamDetails(),
    @SerializedName("scoreDetails") var scoreDetails: ScoreDetails? = ScoreDetails(),
//    @SerializedName("pp") var ppData: ArrayList<PpDataScore> = arrayListOf(),
    @SerializedName("pp"           ) var pp           : Pp?                = Pp(),
//    @SerializedName("fow") var wicketsData: ArrayList<WicketsData> = arrayListOf(),
    @SerializedName("partnership") var partnershipClass: PartnershipClass? = PartnershipClass(),
    @SerializedName("fow"          ) var fow          : Fow?               = Fow(),
    //////////////
    @SerializedName("inningsId"    ) var inningsId    : Int?               = null,
    @SerializedName("batsman"      ) var batsman      : ArrayList<Batsman> = arrayListOf(),
    @SerializedName("bowler"       ) var bowler       : ArrayList<Bowler>  = arrayListOf(),
    @SerializedName("extras"       ) var extras       : ExtrasData?        = ExtrasData(),
//    @SerializedName("pp"           ) var pp           : Pp?                = Pp(),
    @SerializedName("score"        ) var score        : Double?               = 0.0,
    @SerializedName("wickets"      ) var wickets      : Int?               = 0,
    @SerializedName("overs"        ) var overs        : Double?               = 0.0,
    @SerializedName("runRate"      ) var runRate      : Double?            = 0.0,
    @SerializedName("batTeamName"  ) var batTeamName  : String?            = null,
    @SerializedName("batTeamSName" ) var batTeamSName : String?            = null,
    @SerializedName("ballNbr"      ) var ballNbr      : Int?               = 0,
    @SerializedName("rpb"          ) var rpb          : Double?            = null,
)
data class Pp (
    @SerializedName("powerPlay" ) var powerPlay : ArrayList<PpDataScore> = arrayListOf()
)

data class PartnershipClass (
    @SerializedName("partnership" ) var partnership : ArrayList<PartnershipsData> = arrayListOf()
)

data class Fow (
    @SerializedName("fow" ) var fow : ArrayList<WicketsData> = arrayListOf()
)



data class Bowler (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("overs"   ) var overs   : String? = null,
    @SerializedName("wickets" ) var wickets : Int?    = 0,
    @SerializedName("runs"    ) var runs    : Double?    = 0.0,
    @SerializedName("economy" ) var economy : String? = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("balls"   ) var balls   : Int?    = null

)

data class PpDataScore (

    @SerializedName("id"        ) var ppId        : Int?    = null,
    @SerializedName("ovrFrom" ) var ppOversFrom : Double? = null,
    @SerializedName("ovrTo"   ) var ppOversTo   : Double?    = null,
    @SerializedName("ppType"      ) var ppType      : String? = null,
    @SerializedName("run"  ) var runsScored  : Int?    = null

)
data class ScoreDetails(

    @SerializedName("ballNbr") var ballNbr: Double? = null,
    @SerializedName("isDeclared") var isDeclared: Boolean? = null,
    @SerializedName("isFollowOn") var isFollowOn: Boolean? = null,
    @SerializedName("overs") var overs: Double? = null,
    @SerializedName("revisedOvers") var revisedOvers: Double? = null,
    @SerializedName("runRate") var runRate: Double? = null,
    @SerializedName("runs") var runs: Int? = null,
    @SerializedName("wickets") var wickets: Int? = null,
    @SerializedName("runsPerBall") var runsPerBall: Double? = null

)
data class PartnershipsData (

    @SerializedName("bat1Id"         ) var bat1Id         : Long?    = null,
    @SerializedName("bat1Name"       ) var bat1Name       : String? = null,
    @SerializedName("bat1Runs"       ) var bat1Runs       : Int?    = null,
    @SerializedName("bat1fours"      ) var bat1fours      : Int?    = null,
    @SerializedName("bat1sixes"      ) var bat1sixes      : Int?    = null,
    @SerializedName("bat2Id"         ) var bat2Id         : Long?    = null,
    @SerializedName("bat2Name"       ) var bat2Name       : String? = null,
    @SerializedName("bat2Runs"       ) var bat2Runs       : Int?    = null,
    @SerializedName("bat2fours"      ) var bat2fours      : Int?    = null,
    @SerializedName("bat2sixes"      ) var bat2sixes      : Int?    = null,
    @SerializedName("totalRuns"      ) var totalRuns      : Int?    = null,
    @SerializedName("totalBalls"     ) var totalBalls     : Double?    = null,
    @SerializedName("bat1balls"      ) var bat1balls      : Double?    = null,
    @SerializedName("bat2balls"      ) var bat2balls      : Double?    = null,
    @SerializedName("bat1Ones"       ) var bat1Ones       : Int?    = null,
    @SerializedName("bat1Twos"       ) var bat1Twos       : Int?    = null,
    @SerializedName("bat1Threes"     ) var bat1Threes     : Int?    = null,
    @SerializedName("bat1Fives"      ) var bat1Fives      : Int?    = null,
    @SerializedName("bat1Boundaries" ) var bat1Boundaries : Int?    = null,
    @SerializedName("bat1Sixers"     ) var bat1Sixers     : Int?    = null,
    @SerializedName("bat2Ones"       ) var bat2Ones       : Int?    = null,
    @SerializedName("bat2Twos"       ) var bat2Twos       : Int?    = null,
    @SerializedName("bat2Threes"     ) var bat2Threes     : Int?    = null,
    @SerializedName("bat2Fives"      ) var bat2Fives      : Int?    = null,
    @SerializedName("bat2Boundaries" ) var bat2Boundaries : Int?    = null,
    @SerializedName("bat2Sixers"     ) var bat2Sixers     : Int?    = null

)

data class WicketsData (

    @SerializedName("batsmanId") var batId   : Long?    = null,
    @SerializedName("batsmanName") var batName : String? = null,
    @SerializedName("wktNbr") var wktNbr  : Int?    = null,
    @SerializedName("overNbr") var wktOver : Double? = null,
    @SerializedName("runs") var wktRuns : Int?    = null,
    @SerializedName("ballNbr") var ballNbr : Int?    = null

)
data class ExtrasData (

    @SerializedName("noBalls" ) var noBalls : Int? = 0,
    @SerializedName("total"   ) var total   : Int? = 0,
    @SerializedName("byes"    ) var byes    : Int? = 0,
    @SerializedName("penalty" ) var penalty : Double? = 0.0,
    @SerializedName("wides"   ) var wides   : Int? = 0,
    @SerializedName("legByes" ) var legByes : Double? = 0.0

)

data class BatTeamDetails(

    @SerializedName("batTeamId") var batTeamId: Int? = null,
    @SerializedName("batTeamName") var batTeamName: String? = null,
    @SerializedName("batTeamShortName") var batTeamShortName: String? = null,
    @SerializedName("batsmenData") var batsmenData: ArrayList<BatsmenData> = arrayListOf()

)

data class BowlTeamDetails(

    @SerializedName("bowlTeamId") var bowlTeamId: Int? = null,
    @SerializedName("bowlTeamName") var bowlTeamName: String? = null,
    @SerializedName("bowlTeamShortName") var bowlTeamShortName: String? = null,
    @SerializedName("bowlersData") var bowlersData: ArrayList<BowlersData> = arrayListOf()

)

data class BatsmenData(

    @SerializedName("batId") var batId: Long? = null,
    @SerializedName("batName") var batName: String? = null,
    @SerializedName("batShortName") var batShortName: String? = null,
    @SerializedName("isCaptain") var isCaptain: Boolean? = null,
    @SerializedName("isKeeper") var isKeeper: Boolean? = null,
    @SerializedName("runs") var runs: Int? = null,
    @SerializedName("balls") var balls: Int? = null,
    @SerializedName("dots") var dots: Int? = null,
    @SerializedName("fours") var fours: Int? = null,
    @SerializedName("sixes") var sixes: Int? = null,
    @SerializedName("mins") var mins: Int? = null,
    @SerializedName("strikeRate") var strikeRate: Double? = null,
    @SerializedName("outDesc") var outDesc: String? = null,
    @SerializedName("bowlerId") var bowlerId: Int? = null,
    @SerializedName("fielderId1") var fielderId1: Long? = null,
    @SerializedName("fielderId2") var fielderId2: Long? = null,
    @SerializedName("fielderId3") var fielderId3: Long? = null,
    @SerializedName("ones") var ones: Int? = null,
    @SerializedName("twos") var twos: Int? = null,
    @SerializedName("threes") var threes: Int? = null,
    @SerializedName("fives") var fives: Int? = null,
    @SerializedName("boundaries") var boundaries: Int? = null,
    @SerializedName("sixers") var sixers: Int? = null,
    @SerializedName("wicketCode") var wicketCode: String? = null,
    @SerializedName("isOverseas") var isOverseas: Boolean? = null,
    @SerializedName("inMatchChange") var inMatchChange: String? = null,
    @SerializedName("playingXIChange") var playingXIChange: String? = null

)

data class Batsman (

    @SerializedName("id") var id       : Int?     = null,
    @SerializedName("balls"    ) var balls    : Int?     = null,
    @SerializedName("runs"     ) var runs     : Int?     = 0,
    @SerializedName("fours"    ) var fours    : Int?     = 0,
    @SerializedName("sixes"    ) var sixes    : Int?     = 0,
    @SerializedName("strkRate" ) var strkRate : String?  = null,
    @SerializedName("name"     ) var name     : String?  = null,
    @SerializedName("isKeeper" ) var isKeeper : Boolean? = null,
    @SerializedName("outDec"   ) var outDec   : String?  = null

)

data class BowlersData(

    @SerializedName("bowlerId") var bowlerId: Long? = null,
    @SerializedName("bowlName") var bowlName: String? = null,
    @SerializedName("bowlShortName") var bowlShortName: String? = null,
    @SerializedName("isCaptain") var isCaptain: Boolean? = null,
    @SerializedName("isKeeper") var isKeeper: Boolean? = null,
    @SerializedName("overs") var overs: Double? = null,
    @SerializedName("maidens") var maidens: Double? = null,
    @SerializedName("runs") var runs: Double? = null,
    @SerializedName("wickets") var wickets: Int? = null,
    @SerializedName("economy") var economy: Double? = null,
    @SerializedName("no_balls") var noBalls: Double? = null,
    @SerializedName("wides") var wides: Int? = null,
    @SerializedName("dots") var dots: Double? = null,
    @SerializedName("balls") var balls: Double? = null,
    @SerializedName("runsPerBall") var runsPerBall: Double? = null,
    @SerializedName("isOverseas") var isOverseas: Boolean? = null,
    @SerializedName("inMatchChange") var inMatchChange: String? = null,
    @SerializedName("playingXIChange") var playingXIChange: String? = null

)