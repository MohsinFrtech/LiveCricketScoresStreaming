package com.traumsportzone.live.cricket.tv.scores.score.model

import com.google.gson.annotations.SerializedName

data class PlayerDetailClass (

    @SerializedName("id"            ) var id            : String?                = null,
    @SerializedName("bat"           ) var bat           : String?                = null,
    @SerializedName("bowl"          ) var bowl          : String?                = null,
    @SerializedName("name"          ) var name          : String?                = null,
    @SerializedName("nickName"      ) var nickName      : String?                = null,
    @SerializedName("height"        ) var height        : String?                = null,
    @SerializedName("role"          ) var role          : String?                = null,
    @SerializedName("birthPlace"    ) var birthPlace    : String?                = null,
    @SerializedName("intlTeam"      ) var intlTeam      : String?                = null,
    @SerializedName("teams"         ) var teams         : String?                = null,
    @SerializedName("DoB"           ) var DoB           : String?                = null,
    @SerializedName("image"         ) var image         : String?                = null,
    @SerializedName("bio"           ) var bio           : String?                = null,
    @SerializedName("rankings"      ) var rankings      : Rankings?              = Rankings(),
    @SerializedName("appIndex"      ) var appIndex      : AppIndex?              = AppIndex(),
    @SerializedName("DoBFormat"     ) var DoBFormat     : String?                = null,
    @SerializedName("faceImageId"   ) var faceImageId   : Int?                   = null,
    @SerializedName("teamNameIds"   ) var teamNameIds   : ArrayList<TeamNameIds> = arrayListOf(),
    @SerializedName("playerTeamIds" ) var playerTeamIds : String?                = null

)

data class Rankings (

    @SerializedName("bat"  ) var bat  : Bat?  = Bat(),
    @SerializedName("bowl" ) var bowl : Bowl? = Bowl(),
    @SerializedName("all"  ) var all  : All?  = All()

)
data class Bat (

    @SerializedName("testRank"     ) var testRank     : String? = null,
    @SerializedName("testBestRank" ) var testBestRank : String? = null,
    @SerializedName("odiBestRank"  ) var odiBestRank  : String? = null,
    @SerializedName("t20BestRank"  ) var t20BestRank  : String? = null

)
data class Bowl (
    @SerializedName("testRank"     ) var testRank     : String? = null,
    @SerializedName("testBestRank" ) var testBestRank : String? = null,
    @SerializedName("odiBestRank"  ) var odiBestRank  : String? = null,
    @SerializedName("t20BestRank"  ) var t20BestRank  : String? = null
)
data class All (
    @SerializedName("testRank"     ) var testRank     : String? = null,
)

data class TeamNameIds (

    @SerializedName("teamId"   ) var teamId   : String? = null,
    @SerializedName("teamName" ) var teamName : String? = null

)