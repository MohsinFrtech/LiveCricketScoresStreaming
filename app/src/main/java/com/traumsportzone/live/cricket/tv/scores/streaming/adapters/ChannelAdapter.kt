package com.traumsportzone.live.cricket.tv.scores.streaming.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chartboost.sdk.impl.p
import com.facebook.ads.NativeAd
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemLayoutChannelsBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.NativeAdLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.date.ProcessingFile
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Channel
import com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments.ChannelFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.setSafeOnClickListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.USER_AGENT
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cas_Ai
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.channel_url_val
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.dash
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.defaultString
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.hlsSource
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.nativeFacebook
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.passphraseVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.positionClick
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.previousClick
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.sepUrl
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userBase
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userIp
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType1
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType2
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType4
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType5
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userTypePhp
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.xForwardedKey
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation
import java.text.SimpleDateFormat
import java.util.*


class ChannelAdapter(val context: Context, private val navigateData: NavigateData, val list: List<Channel?>,
                     private val adType:String,
                     private val adManager: AdManager,
                     private val localVal:String): ListAdapter<Channel, RecyclerView.ViewHolder>(
    ChannelAdapterDiffUtilCallback
) {


    private var channelAdapterBinding: ItemLayoutChannelsBinding?=null
    private val nativeAdsLayout = 1
    private val simpleMenuLayout = 0
    private var binding2: NativeAdLayoutBinding? = null
    private var fbNativeAd: NativeAd? = null

    object ChannelAdapterDiffUtilCallback: DiffUtil.ItemCallback<Channel>()
    {
        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {

            return oldItem.name==newItem.name
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            nativeAdsLayout -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_layout, parent, false)
                binding2 = DataBindingUtil.bind(view)
                NativeChannelViewHolder(view)
            }
            else -> {

                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_layout_channels, parent, false)
                channelAdapterBinding = DataBindingUtil.bind(view)
                ChannelAdapterViewHolder(view)
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
//        holder.setIsRecyclable(false)
        when (getItemViewType(position)) {
            nativeAdsLayout -> {
                ////For native ads if ads_provider provide native ads..
                val viewHolder: NativeChannelViewHolder = holder as NativeChannelViewHolder

                if (adType.equals(Constants.facebook, true)) {

                    if (Cons.currentNativeAdFacebook != null) {
                        binding2?.fbNativeAdContainer?.let {
                            adManager.inflateFbNativeAd(
                                Cons.currentNativeAdFacebook!!, it
                            )
                        }
                    }
                    else
                    {
                        fbNativeAd = NativeAd(context, nativeFacebook)
                        binding2?.adLoadLay2?.visibility = View.VISIBLE
                        binding2?.fbNativeAdContainer?.let {
                            adManager.loadFacebookNativeAd(
                                fbNativeAd!!,
                                it, binding2?.adLoadLay2
                            )
                        }
                    }
                }
                else if (adType.equals(Constants.adManagerAds, true)) {
                    binding2?.adLoadLay?.visibility = View.VISIBLE
                    binding2?.nativeAdView?.let {
                        adManager.loadAdmobNativeAdWithManager(
                            viewHolder,
                            it,
                            binding2?.adLoadLay
                        )
                    }
                }
                else if (adType.equals(Constants.admob, true)) {

                    if (Cons.currentNativeAd != null) {
                        binding2?.nativeAdView?.let {
                            adManager.populateNativeAdView(
                                Cons.currentNativeAd!!,
                                it
                            )
                        }
//                        binding2?.nativeAdView?.let { adManager.loadAdmobNativeAd(viewHolder, it) }
                    } else {
                        binding2?.adLoadLay?.visibility = View.VISIBLE
                        binding2?.nativeAdView?.let {
                            adManager.loadAdmobNativeAd(
                                viewHolder,
                                it,
                                binding2?.adLoadLay
                            )
                        }
                    }
                }
                else if(adType.equals(cas_Ai, true)){
                    binding2?.casAdContainer?.visibility = View.VISIBLE
                    binding2?.casAdContainer?.let{
                        adManager.loadNativeAdCasAi(binding2?.adLoadLay3,it)
                    }
                }
            }
            else -> {
                ///To set remaining channels
//                channelAdapterBinding?.dataChannel=currentList[position]
                val viewHolder: ChannelAdapterViewHolder = holder as ChannelAdapterViewHolder
                viewHolder.textChannel.text = currentList[position].name
                loadImage(viewHolder.imgChannel,currentList[position].image_url)

                if (position == positionClick) {
                    viewHolder?.channelBack?.setBackgroundResource(R.drawable.new_dot_bg_channel_select)
                } else {
                    viewHolder?.channelBack?.setBackgroundResource(R.drawable.new_dot_bg_channel)
                }
                if (!currentList[position].date.isNullOrEmpty())
                {
                    dateAndTime(currentList[position].date,viewHolder)
                }

                holder.itemView.setSafeOnClickListener{

                    try {
                        Constants.positionClick2 = 0
                        Constants.previousClick2 = -1
//                        setSelected(position)

                        val timeValue: Long = if (!currentList[position].date.isNullOrEmpty()) {
                            getSelectedChannelTimeInMillis(currentList[position].date)
                        } else {
                            0
                        }


                        /// For handling initial time.......
                        if (!currentList[position]?.initial_time.isNullOrEmpty()){
                            val timeFromCms = currentList[position]?.initial_time?.toString()
                            if (timeFromCms != null) {
                                Constants.timeValueAtPlayer = timeFromCms.toInt()
                            }
                            else{
                                Constants.timeValueAtPlayer = 15
                            }
                        }
                        else{
                            Constants.timeValueAtPlayer = 15
                        }
                        ////////////////////////////////////////////


                        positionClick = holder.absoluteAdapterPosition
                        if (previousClick == -1)
                            previousClick = positionClick
                        else {
                            notifyItemChanged(previousClick)
                            previousClick = positionClick
                        }
                        notifyItemChanged(positionClick)


                        if (currentList[position]?.channel_type.equals(
                                userType1, true)) {

                             if (localVal.isNotEmpty()) {
                                 val processingFile = ProcessingFile()
                                 defaultString = processingFile.getChannelType(localVal)
                             }



                            val token= currentList[position].url?.let { it1 -> Defamation.improveDeprecatedCode(it1) }

                            val linkAppend = currentList[position].url + token

                            val channelDirection=ChannelFragmentDirections.actionChannelToPlayer2(currentList[position].url, linkAppend,
                                userType1,timeValue)
                            navigateData.navigation(channelDirection)
                        }
                        else if (currentList[position]?.channel_type.equals(
                                dash, true)){
                            //for playing dash media...
                            Constants.clearKeyKey =""
                            USER_AGENT = "ExoPlayer-Drm"
                            xForwardedKey =""
                            ///Dash media source...
                            if (!currentList[position]?.clear_key.isNullOrEmpty())
                            {
                                Constants.clearKeyKey = currentList[position]?.clear_key.toString()
                            }

                            //User Agent...
                            if (!currentList[position]?.user_agent.isNullOrEmpty()){
                                USER_AGENT = currentList[position]?.user_agent.toString()
                            }

                            if (!currentList[position]?.forwarded_for.isNullOrEmpty()){
                                xForwardedKey = currentList[position]?.forwarded_for.toString()
                            }
                            /////
                            val channelDirection=ChannelFragmentDirections.actionChannelToPlayer2(currentList[position].url, currentList[position].url,
                                dash,timeValue)
                            navigateData.navigation(channelDirection)
                        }
                        else if (currentList[position]?.channel_type.equals(
                                userTypePhp, true)){
                            //for playing dash media...
                            Constants.clearKeyKey =""
                            USER_AGENT = "ExoPlayer-Drm"
                            xForwardedKey =""
                            ///Dash media source...
                            if (!currentList[position]?.clear_key.isNullOrEmpty())
                            {
                                Constants.clearKeyKey = currentList[position]?.clear_key.toString()
                            }

                            //User Agent...
                            if (!currentList[position]?.user_agent.isNullOrEmpty()){
                                USER_AGENT = currentList[position]?.user_agent.toString()
                            }

                            if (!currentList[position]?.forwarded_for.isNullOrEmpty()){
                                xForwardedKey = currentList[position]?.forwarded_for.toString()
                            }
                            /////
                            val channelDirection=ChannelFragmentDirections.actionChannelToPlayer2(currentList[position].url, currentList[position].url,
                                userTypePhp,timeValue)
                            navigateData.navigation(channelDirection)
                        }
                        else if (currentList[position]?.channel_type.equals(
                                userType2, true))
                        {

                            if (currentList[position]?.url?.contains(sepUrl) == true
                                && passphraseVal.isNotEmpty()) {

                                val separatedPart =
                                    currentList[position]?.url?.split(sepUrl)

                                channel_url_val = separatedPart?.get(1).toString()

                                val channelDirection =
                                    ChannelFragmentDirections.actionChannelToPlayer2(
                                        "baseLink",
                                        "linkAppend", userType2,timeValue
                                    )
                                navigateData.navigation(channelDirection)

                            }
                        }
                        else if (currentList[position]?.channel_type.equals(
                                userType3, true))
                        {
                            if (localVal.isNotEmpty()) {
                                val processingFile = ProcessingFile()
                                defaultString = processingFile.getChannelType(localVal)
                            }

                            val token= currentList[position].url?.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                            val linkAppend = currentList[position].url + token
                            val channelDirection=ChannelFragmentDirections.actionChannelToPlayer2(currentList[position].url, linkAppend,
                                userType3,timeValue)
                            navigateData.navigation(channelDirection)


                        }else if (currentList[position]?.channel_type.equals(
                                hlsSource, true)){
                            //for playing dash media...
                            Constants.clearKeyKey =""
                            USER_AGENT = "ExoPlayer-Drm"
                            xForwardedKey =""
                            ///Dash media source...
                            if (!currentList[position]?.clear_key.isNullOrEmpty())
                            {
                                Constants.clearKeyKey = currentList[position]?.clear_key.toString()
                            }
                            //User Agent...
                            if (!currentList[position]?.user_agent.isNullOrEmpty()){
                                USER_AGENT = currentList[position]?.user_agent.toString()
                            }

                            if (!currentList[position]?.forwarded_for.isNullOrEmpty()){
                                xForwardedKey = currentList[position]?.forwarded_for.toString()
                            }

                                val channelDirection =
                                    ChannelFragmentDirections.actionChannelToPlayer2(
                                        currentList[position].url, currentList[position].url,
                                        hlsSource,timeValue
                                    )
                                navigateData.navigation(channelDirection)


                        }
                        else if (currentList[position]?.channel_type.equals(
                                userType5, true)){
                            val urlToPlay = currentList[position]?.url
                            if (!urlToPlay.isNullOrEmpty()){
                                try {
                                    val url = urlToPlay
                                    val i = Intent(Intent.ACTION_VIEW)
                                    i.data = Uri.parse(url)
                                    context?.startActivity(i)
                                } catch (e: ActivityNotFoundException) {
//                                    logger.printLog(tags, "exception : ${e.localizedMessage}")
                                }
                            }
                        }
                        else
                        {
                            if (localVal.isNotEmpty()) {
                                val processingFile = ProcessingFile()
                                defaultString = processingFile.getChannelType(localVal)
                            }

                            val token= currentList[position].url?.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                            val linkAppend = currentList[position].url + token
                            val channelDirection=ChannelFragmentDirections.actionChannelToPlayer2(currentList[position].url, linkAppend,
                                userType1,timeValue)
                            navigateData.navigation(channelDirection)
                        }
                    }
                    catch (e:Exception)
                    {
                        Log.d("Token","exception"+e.message)
                    }

                }
            }

        }

    }

    private fun getSelectedChannelTimeInMillis(channelDate: String?): Long {
        try {
            val df = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date = channelDate?.let { df.parse(it) }
            df.timeZone = TimeZone.getDefault()
            val formattedDate = date?.let { df.format(it) }
            val date2: Date? = formattedDate?.let { df.parse(it) }
            val calendar = Calendar.getInstance()
            if (date2 != null) {
                calendar.time = date2
            }
            return calendar.timeInMillis
        } catch (e: Exception) {
            return 0
        }
    }


    private fun dateAndTime(channelDate: String?, viewHolder: ChannelAdapterViewHolder) {
        val df = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = channelDate?.let { df.parse(it) }
        df.timeZone = TimeZone.getDefault()
        val formattedDate = date?.let { df.format(it) }
        val date2: Date? = formattedDate?.let { df.parse(it) }
        val cal = Calendar.getInstance()
        if (date2 != null) {
            cal.time = date2
        }
        var hours = cal[Calendar.HOUR_OF_DAY]
        val minutes = cal[Calendar.MINUTE]
        var timeInAmOrPm = ""

        if (hours > 0) {
            timeInAmOrPm = if (hours >= 12) {
                "PM"
            } else {
                "AM"
            }
        }


        if (hours > 0) {
            if (hours >= 12) {
                if (hours == 12) {
                    /////
                } else {
                    hours -= 12
                }
            }
        }

        if (hours == 0) {
            hours = 12
            timeInAmOrPm = "AM"
        }

        val dayOfTheWeek =
            DateFormat.format("EEEE", date) as String

        val day = DateFormat.format("dd", date) as String

        val monthString =
            DateFormat.format("MMM", date) as String
        val year = DateFormat.format("yyyy", date) as String

        var timeValue=""
        if (minutes < 9) {
            timeValue =
                "$hours:0$minutes $timeInAmOrPm"
        } else {
            timeValue =
                "$hours:$minutes $timeInAmOrPm"
        }

        viewHolder?.textDate?.text =
            "$dayOfTheWeek,$day $monthString $year $timeValue"
        viewHolder?.textDate?.visibility = View.VISIBLE
    }

    class ChannelAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgChannel = itemView.findViewById<ImageView>(R.id.channelImage)
        val textChannel = itemView.findViewById<TextView>(R.id.channelName)
        val textDate = itemView.findViewById<TextView>(R.id.dateOfChannel)
        val channelBack = itemView.findViewById<ConstraintLayout>(R.id.channel_back)

    }

    ///View holder class
    class NativeChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}