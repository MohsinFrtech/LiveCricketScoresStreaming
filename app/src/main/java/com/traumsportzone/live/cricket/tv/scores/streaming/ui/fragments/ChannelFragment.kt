package com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentChannelsBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.adapters.ChannelAdapter
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Channel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import java.util.ArrayList

class ChannelFragment: Fragment(), NavigateData, AdManagerListener {
    private var bindingChannel: FragmentChannelsBinding?=null
    private var adManager: AdManager?=null
    private var adStatus=false
    private var navDirections: NavDirections?=null
    private var adProviderName="none"
    private var nativeAdProviderName="none"
    private var nativeFieldVal=""
    private var listWithAd: List<Channel?> =
        ArrayList<Channel?>()
    private val viewModel by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_channels,container,false)
        bindingChannel= DataBindingUtil.bind(view)
        bindingChannel?.lifecycleOwner=this
        adManager= context?.let { activity?.let { it1 -> AdManager(it, it1, this) } }
        if (Constants.middleAdProvider.equals(Constants.startApp,true))
        {
            Log.d("providerval","mid"+ Constants.middleAdProvider)

            adManager?.loadAdProvider(
                Constants.middleAdProvider,
                Constants.adMiddle, null,null,null,null)

        }
        bindingChannel?.btnBack?.setOnClickListener{

            this.findNavController().popBackStack()
        }



        return view
    }

    override fun onResume() {
        super.onResume()
        settingChannels()
    }

    ///set channels
    private fun settingChannels() {
        val channelData: ChannelFragmentArgs by navArgs()
        if (channelData.getEvent!=null)
        {
            bindingChannel?.eventNameChannel?.text= channelData.getEvent!!.name
            setChannelAdapter(channelData.getEvent?.channels)
        }

    }

    private fun setChannelAdapter(channels: List<Channel>?) {

        viewModel?.dataModelList?.observe(viewLifecycleOwner)
        {

            if (!it.extra_3.isNullOrEmpty())
            {
                nativeFieldVal= it.extra_3!!
            }
            if (!it.app_ads.isNullOrEmpty())
            {
                adProviderName= adManager?.checkProvider(it.app_ads!!, Constants.adBefore).toString()
                nativeAdProviderName=adManager?.checkProvider(it.app_ads!!,
                    Constants.nativeAdLocation
                ).toString()
                Constants.location2TopProvider =adManager?.checkProvider(it.app_ads!!,
                    Constants.adLocation2top
                ).toString()
                Constants.location2BottomProvider =adManager?.checkProvider(it.app_ads!!,
                    Constants.adLocation2bottom
                ).toString()
                Constants.location2TopPermanentProvider =adManager?.checkProvider(it.app_ads!!,
                    Constants.adLocation2topPermanent
                ).toString()
                Constants.locationAfter =adManager?.checkProvider(it.app_ads!!, Constants.adAfter).toString()
                if (adProviderName.equals(Constants.startApp,true))
                {
                    if (Constants.videoFinish) {
                        Constants.videoFinish = false
                        adManager?.loadAdProvider(adProviderName,
                            Constants.adBefore, null,null,null,null)

                    }
                }
                else
                {
                    adManager?.loadAdProvider(adProviderName,
                        Constants.adBefore, null,null,null,null)

                }


            }
            val liveChannels: MutableList<Channel> =
                ArrayList<Channel>()
            for (channel in channels!!)
            {
                if (channel.live==true)
                {
                    liveChannels.add(channel)
                }

            }

            if (liveChannels.isNotEmpty())
            {
                bindingChannel?.noChannelIcon?.visibility= View.GONE
                bindingChannel?.noChannelText?.visibility= View.GONE
                liveChannels.sortBy { it1 ->
                    it1.priority
                }

                listWithAd = if (nativeAdProviderName.equals(Constants.admob,true)) {
                    checkNativeAd(liveChannels)
                } else if (nativeAdProviderName.equals(Constants.facebook,true)) {
                    checkNativeAd(liveChannels)
                } else {
                    liveChannels
                }

                val adapter= context?.let { adManager?.let { it1 ->
                    ChannelAdapter(
                        it, this, listWithAd, nativeAdProviderName,
                        it1, nativeFieldVal
                    )
                } }
                bindingChannel?.channelRecycler?.layoutManager= LinearLayoutManager(context)
                bindingChannel?.channelRecycler?.adapter=adapter
                adapter?.submitList(listWithAd)
            }
            else
            {
                bindingChannel?.noChannelIcon?.visibility= View.VISIBLE
                bindingChannel?.noChannelText?.visibility= View.VISIBLE

            }
        }


    }

    override fun navigation(viewId: NavDirections) {
        try {
            navDirections=viewId
            if (adStatus)
            {
                if (!adProviderName.equals("none",true)) {
                    adManager?.showAds(adProviderName)
                }
                else
                {
                    findNavController().navigate(viewId)
                }
            }
            else {
                findNavController().navigate(viewId)
            }
        }
        catch (e:Exception)
        {
            Log.d("Exception", "message")
        }


    }


    ////Function to return list of events with empty positions.....
    private fun checkNativeAd(list: List<Channel>): List<Channel?> {
        val tempChannels: MutableList<Channel?> =
            ArrayList()
        for (i in list.indices) {
            if (list[i].live!!) {
                val diff = i % 5
                if (diff == 2) {

                    tempChannels.add(null)
                }
                tempChannels.add(list[i])
                if (list.size == 2) {
                    if (i == 1) {
                        tempChannels.add(null)

                    }
                }
            }
        }
        return tempChannels
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success",true)

    }

    override fun onAdFinish() {

        if (navDirections!=null)
        {
            findNavController().navigate(navDirections!!)
        }
    }


}