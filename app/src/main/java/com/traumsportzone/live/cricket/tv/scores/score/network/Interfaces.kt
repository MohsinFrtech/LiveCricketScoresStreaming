package com.traumsportzone.live.cricket.tv.scores.score.network

import com.traumsportzone.live.cricket.tv.scores.score.model.AllTeamsModel
import com.traumsportzone.live.cricket.tv.scores.score.model.CommentryModelClass
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.model.NewsModel
import com.traumsportzone.live.cricket.tv.scores.score.model.NewsdetailModel
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayersRankingModel
import com.traumsportzone.live.cricket.tv.scores.score.model.RankingTeams
import com.traumsportzone.live.cricket.tv.scores.score.model.ScoreboardModel
import com.traumsportzone.live.cricket.tv.scores.score.model.SeriesScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.model.SquadModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Interfaces {

    @POST("matches/live")
    fun getLiveDataAsync(
        @Body body: RequestBody
    ): Call<List<LiveScoresModel>>


    @POST("commentaries/match")
    fun getMatchCommentary(
        @Body body: RequestBody
    ): Call<CommentryModelClass>

    @POST("matches/recent")
    fun getRecentDataAsync(
        @Body body: RequestBody
    ): Call<List<LiveScoresModel>>


    @POST("matches/upcoming")
    fun getUpcomingDataAsync(
        @Body body: RequestBody
    ): Call<List<LiveScoresModel>>



    @POST("serieses/all")
    fun seriesDataAsync(
        @Body body: RequestBody
    ): Call<List<SeriesScoresModel>>


    @POST("serieses/{number}/matches")
    fun seriesSeriesMatches(
        @Body body: RequestBody,
        @Path("number") number: Int
    ): Call<List<LiveScoresModel>>


    @POST("team_rankings/odi")
    fun getOdiRankingDataAsync(
        @Body body: RequestBody
    ): Call<List<RankingTeams>>

    @POST("team_rankings/t20")
    fun getT20RankingDataAsync(
        @Body body: RequestBody
    ): Call<List<RankingTeams>>

    @POST("team_rankings/test")
    fun getTestRankingDataAsync(
        @Body body: RequestBody
    ): Call<List<RankingTeams>>

    @POST("teams/all")
    fun getTeamsDataAsync(
        @Body body: RequestBody
    ): Call<List<AllTeamsModel>>

    @POST("teams/{number}/matches")
    fun getMatchesList(
        @Body body: RequestBody,
        @Path("number") number: Int
    ): Call<List<LiveScoresModel>>

    @POST("player_rankings/odi")
    fun getOdiRankingPlayerAsync(
        @Body body: RequestBody
    ): Call<List<PlayersRankingModel>>

    @POST("player_rankings/t20")
    fun getT20RankingPlayerAsync(
        @Body body: RequestBody
    ): Call<List<PlayersRankingModel>>

    @POST("player_rankings/test")
    fun getTestRankingPlayerAsync(
        @Body body: RequestBody
    ): Call<List<PlayersRankingModel>>

    //get team squad info....
    @POST("matches/match_info")
    fun getTeamSquadInfo(
        @Body body: RequestBody
    ): Call<SquadModel?>

    //get Scoreboard info....
    @POST("matches/scorecard")
    fun getScoreboard(
        @Body body: RequestBody
    ): Call<ScoreboardModel?>

    //Get all news....
    @POST("news/list")
    fun getAllNews(
        @Body body: RequestBody
    ): Call<NewsModel?>

    //News detail ....
    @POST("news/details")
    fun getNewsDetail(
        @Body body: RequestBody
    ): Call<NewsdetailModel?>
}