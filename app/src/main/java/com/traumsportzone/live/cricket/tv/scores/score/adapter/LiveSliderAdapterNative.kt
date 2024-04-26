package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.MainFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.LiveSliderLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.NativeAdLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.NativeAdLayoutNew2Binding
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.RecentFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.SeriesMatchFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.convertLongToTime
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class LiveSliderAdapterNative(
    val context: Context,
    private val navigateData: NavigateData,
    val source: String,
    private val list: List<LiveScoresModel?>,
    private val adType: String,
    private val adManager: AdManager
) :
    ListAdapter<LiveScoresModel, RecyclerView.ViewHolder>(
        LiveSliderAdapterDiffUtilCallback
    ) {

    private var binding: LiveSliderLayoutBinding? = null
    private val nativeAdsLayout = 1
    private val simpleMenuLayout = 0
    private var binding2: NativeAdLayoutBinding? = null


    class LiveSliderAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    ///Ads View holder class
    class NativeAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object LiveSliderAdapterDiffUtilCallback : DiffUtil.ItemCallback<LiveScoresModel>() {
        override fun areItemsTheSame(oldItem: LiveScoresModel, newItem: LiveScoresModel): Boolean {
            return oldItem.match_id == newItem.match_id
        }

        override fun areContentsTheSame(
            oldItem: LiveScoresModel,
            newItem: LiveScoresModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            nativeAdsLayout -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_layout, parent, false)
                binding2 = DataBindingUtil.bind(view)
                NativeAdViewHolder(view)
            }
            else -> {

                val view = LayoutInflater.from(context)
                    .inflate(R.layout.live_slider_layout, parent, false)
                binding = DataBindingUtil.bind(view)
                LiveSliderAdapterViewHolder(view)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] == null) {
            return nativeAdsLayout
        }
        return simpleMenuLayout
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        when (getItemViewType(position)) {
            nativeAdsLayout -> {
                ////For native ads if ads_provider provide native ads..
                val viewHolder: NativeAdViewHolder = holder as NativeAdViewHolder

                if (adType.equals(Constants.facebook, true)) {

//                    val params = binding2?.fbNativeAdContainer?.layoutParams
//                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
//                    params?.height = 432
//                    binding2?.fbNativeAdContainer?.layoutParams = params

                    if (Cons.currentNativeAdFacebook != null) {
                        binding2?.fbNativeAdContainer?.let {
                            adManager.inflateFbNativeAd(
                                Cons.currentNativeAdFacebook!!,it
                            )
                        }
                    }
                } else if (adType.equals(Constants.admob, true)) {

                    /*val params = binding2?.nativeAdView?.layoutParams
                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = 432
                    binding2?.nativeAdView?.layoutParams = params*/


                    if (Cons.currentNativeAd != null) {
                        binding2?.nativeAdView?.let {
                            adManager?.populateNativeAdView(
                                Cons.currentNativeAd!!,
                                it
                            )
                        }
                    }

                }
            }
            else -> {
                ///To set remaining events
                binding?.model = currentList[position]
                val data = currentList[position]
                binding?.executePendingBindings()

                /*val params = binding?.layoutSlider?.layoutParams

                com.yummy.streaming.live.cricket.utils.Logger().printLog("params",
                    "${params?.width} : ${params?.height}")*/

                when (data.team_1_id) {
                    4 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.australia)
                    }
                    2 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.india)
                    }
                    6 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.bangladesh)
                    }
                    5 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.srilanka)
                    }
                    13 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.newzealand)
                    }
                    9 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.england)
                    }
                    3 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.pakistan)
                    }
                    10 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.westindies)
                    }
                    96 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.afghanistan)
                    }
                    11 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.southafrica)
                    }
                    27 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.ireland)
                    }
                    23 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.scotland)
                    }
                    12 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.zimbabwe)
                    }
                    24 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.netherland)
                    }
                    72 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.nepal)
                    }
                    304 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.oman)
                    }
                    161 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.namibia)
                    }
                    15 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.us)
                    }
                    7 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.uae)
                    }
                    287 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.papuanew)
                    }
                    63 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.kolkta_knight_rider)
                    }
                    59 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
                    }
                    64 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.rajistan_royals)
                    }
                    65 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.punjabkings)
                    }
                    61 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.delhi_capitals)
                    }
                    971 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.gujrat_titans)
                    }
                    58 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.chennai_super_kings)
                    }
                    26 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.canada)
                    }
                    255 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.sunrises_hyderabad)
                    }
                    62 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.mumbai_indians)
                    }
                    966 -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.lucknow_super_gaints)
                    }
                    else -> {
                        binding?.ivFirstTeam?.setImageResource(R.drawable.placeholder)
                    }
                }

                when (data.team_2_id) {
                    4 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.australia)
                    }
                    2 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.india)
                    }
                    6 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.bangladesh)
                    }
                    5 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.srilanka)
                    }
                    13 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.newzealand)
                    }
                    9 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.england)
                    }
                    3 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.pakistan)
                    }
                    10 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.westindies)
                    }
                    96 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.afghanistan)
                    }
                    11 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.southafrica)
                    }
                    27 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.ireland)
                    }
                    23 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.scotland)
                    }
                    12 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.zimbabwe)
                    }
                    24 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.netherland)
                    }
                    72 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.nepal)
                    }
                    304 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.oman)
                    }
                    161 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.namibia)
                    }
                    15 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.us)
                    }
                    7 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.uae)
                    }
                    287 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.papuanew)
                    }
                    63 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.kolkta_knight_rider)
                    }
                    59 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
                    }
                    64 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.rajistan_royals)
                    }
                    65 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.punjabkings)
                    }
                    61 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.delhi_capitals)
                    }
                    971 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.gujrat_titans)
                    }
                    58 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.chennai_super_kings)
                    }
                    26 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.canada)
                    }
                    255 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.sunrises_hyderabad)
                    }
                    62 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.mumbai_indians)
                    }
                    966 -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.lucknow_super_gaints)
                    }
                    else -> {
                        binding?.ivSecondTeam?.setImageResource(R.drawable.placeholder)
                    }
                }


                //////////////////////////////////////////////////////////////////////////////////////

                var wicketsTeam1In1 = ""
                var wicketsTeam1In2 = ""
                var wicketsTeam2In1 = ""
                var wicketsTeam2In2 = ""

                wicketsTeam1In1 = if (data.score_card?.team1Score?.inngs1?.wickets != null) {
                    data.score_card?.team1Score?.inngs1?.wickets.toString()
                } else {
                    "0"
                }
                wicketsTeam1In2 = if (data.score_card?.team1Score?.inngs2?.wickets != null) {
                    data.score_card?.team1Score?.inngs2?.wickets.toString()
                } else {
                    "0"
                }
                wicketsTeam2In1 = if (data.score_card?.team2Score?.inngs1?.wickets != null) {
                    data.score_card?.team2Score?.inngs1?.wickets.toString()
                } else {
                    "0"
                }
                wicketsTeam2In2 = if (data.score_card?.team2Score?.inngs2?.wickets != null) {
                    data.score_card?.team2Score?.inngs2?.wickets.toString()
                } else {
                    "0"
                }

                if (data.match_format.equals(Cons.match_format_test, true)) {
                    var scoresTeam1 = ""
                    var scoresTeam2 = ""
                    var oversTeam1 = ""

                    var oversTeam2 = ""


                    if (data.score_card?.team1Score?.inngs1 != null) {
                        if (data.score_card?.team1Score?.inngs2 != null) {

                            scoresTeam1 =
                                data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                        wicketsTeam1In1 + " & " +
                                        data.score_card?.team1Score?.inngs2?.runs.toString() + "/" +
                                        wicketsTeam1In2

                            oversTeam1 = ""
                        } else {

                            scoresTeam1 =
                                data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                        wicketsTeam1In1
                            oversTeam1 =
                                data.score_card?.team1Score?.inngs1?.overs.toString() + " overs"
                        }
                        binding?.tvTeam1Score?.text = scoresTeam1
                        binding?.tvTeam1Over?.text = oversTeam1
                    }
                    if (data.score_card?.team2Score?.inngs1 != null) {
                        if (data.score_card?.team2Score?.inngs2 != null) {

                            scoresTeam2 =
                                data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                        wicketsTeam2In1 + " & " +
                                        data.score_card?.team2Score?.inngs2?.runs.toString() + "/" +
                                        wicketsTeam2In2

                            oversTeam2 = ""
                        } else {

                            scoresTeam2 =
                                data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                        wicketsTeam2In1
                            oversTeam2 =
                                data.score_card?.team2Score?.inngs1?.overs.toString() + " overs"
                        }
                        binding?.tvTeam2Score?.text = scoresTeam2
                        binding?.tvTeam2Over?.text = oversTeam2
                    }
                } else {
                    if (data.score_card?.team1Score?.inngs1 != null) {
                        binding?.tvTeam1Score?.text =
                            data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                    wicketsTeam1In1
                        binding?.tvTeam1Over?.text =
                            data.score_card?.team1Score?.inngs1?.overs.toString() + " overs"
                    }

                    if (data.score_card?.team2Score?.inngs1 != null) {
                        binding?.tvTeam2Score?.text =
                            data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                    wicketsTeam2In1
                        binding?.tvTeam2Over?.text =
                            data.score_card?.team2Score?.inngs1?.overs.toString() + " overs"
                    }
                }

                //////////////////////////////////////////////////////////////////////////////////////

                if (data.start_time != null && data.end_time != null) {
                    binding?.tvTime?.text =
                        convertLongToTime(data.start_time!!) + " - " + convertLongToTime(data.end_time!!)
                }
                holder.itemView.setOnClickListener {
                    if (!data.status.isNullOrEmpty()) {
                        if (source.equals("main", true)) {

                            val itemDirection = MainFragmentDirections.actionLiveToDetails(data)
                            navigateData.navigation(itemDirection)
                        } else if (source.equals("recent", true)) {

                            val itemDirection2 =
                                RecentFragmentDirections.actionRecentFragmentToLiveDetails(data)
                            navigateData.navigation(itemDirection2)
                        } else if (source.equals("seriesMatch", true)) {
                            if (!data.status.equals("Upcoming", true)) {

                                val itemDirection2 =
                                    SeriesMatchFragmentDirections.actionSeriesMatchFragmentToLiveDetails(
                                        data
                                    )
                                navigateData.navigation(itemDirection2)
                            }


                        }

                    }
                }
            }
        }
    }


}