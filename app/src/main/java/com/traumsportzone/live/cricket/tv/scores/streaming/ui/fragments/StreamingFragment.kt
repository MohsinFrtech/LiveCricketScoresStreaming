package com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.ads.NativeAd
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentEventBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.StreamingScreenBinding
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.streaming.adapters.EventAdapter
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.NewAdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Event
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.AppContextProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.adLocation1Provider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cas_Ai
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.checkNativeAdProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.middleAdProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.nativeAdLocation
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.nativeFacebook
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel

class StreamingFragment : Fragment(), NavigateData, AdManagerListener {
    var binding: StreamingScreenBinding? = null
    private val modelEvent by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var liveChannelCount = 0
    private var adManager: AdManager? = null
    private var navDirections: NavDirections? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.streaming_screen, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.model = modelEvent
        adManager = AdManager(requireContext(), requireActivity(), this)
        NewAdManager.setAdManager(this)
        setUpViewModel()
        return view
    }

    private fun setUpViewModel() {
        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {
            if (it.live == true) {
                binding?.noLiveStream?.visibility = View.GONE

                if (!it.events.isNullOrEmpty()) {
                    liveChannelCount = 0
                    val liveEvents: MutableList<Event> =
                        ArrayList<Event>()
                    if (!it.app_ads.isNullOrEmpty()) {
                        checkNativeAdProvider =
                            adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                                .toString()
                        adLocation1Provider =
                            adManager?.checkProvider(it.app_ads!!, Constants.adLocation1).toString()
                    }

                    for (event in it.events!!) {
                        var event_belongs_country = false
                        if (event.live == true) {
                            if (!event.country_codes.isNullOrEmpty()){
                                event.country_codes!!.forEach {
                                        code->
                                    Log.d("countryCode", code)

                                    if (code?.equals(Constants.currentCountryCode, true)== true){
                                        event_belongs_country = true
                                    }
                                }

                                if (event_belongs_country){
                                    if (!event.channels.isNullOrEmpty()) {

                                        for (channel in event.channels!!) {
                                            if (channel.live == true) {
                                                liveChannelCount++
                                            }
                                        }

                                        if (liveChannelCount > 0) {
                                            liveEvents.add(event)
                                        }
                                    }
                                }
                            }else{
                                if (!event.channels.isNullOrEmpty()) {

                                    for (channel in event.channels!!) {
                                        if (channel.live == true) {
                                            liveChannelCount++
                                        }
                                    }

                                    if (liveChannelCount > 0) {
                                        liveEvents.add(event)
                                    }
                                }
                            }
                        }
                    }

                    if (liveEvents.isNotEmpty()) {
                        binding?.gameRecycler?.visibility = View.VISIBLE
                        binding?.noLiveStream?.visibility = View.GONE

                        liveEvents.sortBy { it1 ->
                            it1.priority
                        }
                        setAdapter(liveEvents)
                    } else {
                        binding?.noLiveStream?.visibility = View.VISIBLE
                        binding?.gameRecycler?.visibility = View.GONE
                    }

                } else {
                    binding?.noLiveStream?.visibility = View.VISIBLE
                    binding?.gameRecycler?.visibility = View.GONE

                }
            } else {
                binding?.noLiveStream?.visibility = View.VISIBLE
                binding?.gameRecycler?.visibility = View.GONE

            }
        }

    }

    private fun setAdapter(liveEvents: MutableList<Event>) {
        val listAdapter = context?.let {
            adManager?.let { it1 ->
                EventAdapter(
                    it, this, liveEvents, checkNativeAdProvider,
                    it1
                )
            }
        }
        binding?.gameRecycler?.layoutManager = LinearLayoutManager(context)
        binding?.gameRecycler?.adapter = listAdapter
        listAdapter?.submitList(liveEvents)

    }


    override fun navigation(viewId: NavDirections) {
        try {

            Constants.positionClick=-1
            Constants.previousClick=-1
            navDirections = viewId
            if (!middleAdProvider.equals("none", true)) {
                if (!middleAdProvider.equals(Constants.startApp,true)) {
                    binding?.MainLottie?.visibility = View.VISIBLE
                    val local = AppContextProvider.getContext()
                    local?.let { NewAdManager.showAds(middleAdProvider, requireActivity(), it) }
                }
                else{
                    findNavController().navigate(viewId)
                }
            }
            else{
                findNavController().navigate(viewId)
            }
        } catch (e: java.lang.Exception) {
            Log.d("Exception", "message")
        }
    }


    override fun onResume() {
        super.onResume()
        NewAdManager.setAdManager(this)
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {
        if (binding?.MainLottie?.isVisible == true){
            binding?.MainLottie?.visibility=View.GONE
        }
        if (navDirections != null) {
            findNavController().navigate(navDirections!!)
        }
    }
}