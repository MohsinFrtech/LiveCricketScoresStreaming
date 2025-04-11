package com.traumsportzone.live.cricket.tv.scores

import android.content.res.Resources
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
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.facebook.ads.NativeAd
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentEventBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.LiveViewModel
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
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd.checkNativeAd
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import kotlin.math.abs

class MainFragment : Fragment(), NavigateData, AdManagerListener {


    var binding: FragmentEventBinding? = null
    private val modelEvent by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var nativeAdProviderName = "none"
    private var adManager: AdManager? = null
    private var liveChannelCount = 0
    private var navDirections: NavDirections? = null
    private var adStatus = false
    private val liveViewModel by lazy {
        ViewModelProvider(requireActivity())[LiveViewModel::class.java]
    }
    private var fbNativeAd: NativeAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.model = modelEvent
        binding?.viewModel = liveViewModel


        adManager = AdManager(requireContext(), requireActivity(), this)
        NewAdManager.setAdManager(this)

        if (InternetUtil.isInternetOn(requireContext())) {
//            binding?.progressBar?.visibility=View.VISIBLE
            //liveViewModel.getsliderData()
            //liveViewModel.runSockets()

            setUpViewModel()
        }

//        setUpViewModel()
        return view
    }


    override fun onResume() {
        super.onResume()
        NewAdManager.setAdManager(this)

    }

    private fun setUpViewModel() {

        // Observe Live Data for updating Data in slider
        liveViewModel.sliderList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding?.noStream?.visibility = View.GONE
                setAdapter2(it)
            }
            else{
                binding?.noStream?.visibility = View.VISIBLE
            }
        }
        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {

            adStatus = false
            if (it.live == true) {

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


                        if (checkNativeAdProvider.equals(Constants.admob, true)) {
                            if (Cons.currentNativeAd != null) {
                                binding?.nativeAdView?.let {
                                    adManager?.populateNativeAdView(
                                        Cons.currentNativeAd!!,
                                        it
                                    )
                                }
                            }
                            else {
                                binding?.adLoadLay?.visibility = View.VISIBLE
                                binding?.nativeAdView?.let {
                                    adManager?.loadAdmobNativeAd(
                                        null,
                                        it,
                                        binding?.adLoadLay
                                    )
                                }
                            }

                        }else if (checkNativeAdProvider.equals(Constants.adManagerAds, true)) {
                            binding?.adLoadLay?.visibility = View.VISIBLE
                            binding?.nativeAdView?.let {
                                adManager?.loadAdmobNativeAdWithManager(
                                    null,
                                    it,
                                    binding?.adLoadLay
                                )
                            }
                        }

                        else if (checkNativeAdProvider.equals(Constants.facebook, true)) {

                            if (Cons.currentNativeAdFacebook != null) {
                                binding?.fbNativeAdContainer?.let {
                                    adManager?.inflateFbNativeAd(
                                        Cons.currentNativeAdFacebook!!, it
                                    )
                                }
                            }
                            else {
                                fbNativeAd = NativeAd(context, nativeFacebook)
                                binding?.adLoadLay2?.visibility = View.VISIBLE
                                binding?.fbNativeAdContainer?.let {
                                    adManager?.loadFacebookNativeAd(
                                        fbNativeAd!!,
                                        it, binding?.adLoadLay2
                                    )
                                }
                            }
                        }else if(checkNativeAdProvider.equals(cas_Ai, true)){
                            binding?.casAdContainer?.visibility = View.VISIBLE
                            binding?.casAdContainer?.let{
                                adManager?.loadNativeAdCasAi(binding?.adLoadLay3,it)
                            }
                        }

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
                        showStreaming()
//
                        liveEvents.sortBy { it1 ->
                            it1.priority
                        }
                        setAdapter(liveEvents)
                    } else {
                        hideStreaming()
                        //if event list is empty....
                    }

                } else {
                    hideStreaming()
                    ///if event list is empty from backend...
                    if (!it.app_ads.isNullOrEmpty()) {

                        checkNativeAdProvider =
                            adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                                .toString()
                        if (!checkNativeAdProvider.equals("none", true)) {
                            adManager?.loadAdProvider(
                                checkNativeAdProvider,
                                nativeAdLocation,
                                null,
                                null,
                                null,
                                null
                            )
                        }

                        if (checkNativeAdProvider.equals(Constants.admob, true)) {
                            if (Cons.currentNativeAd != null) {
                                binding?.nativeAdView?.let {
                                    adManager?.populateNativeAdView(
                                        Cons.currentNativeAd!!,
                                        it
                                    )
                                }
                            } else {
                                binding?.adLoadLay?.visibility = View.VISIBLE
                                binding?.nativeAdView?.let {
                                    adManager?.loadAdmobNativeAd(
                                        null,
                                        it,
                                        binding?.adLoadLay
                                    )
                                }
                            }

                        } else if (checkNativeAdProvider.equals(Constants.facebook, true)) {
                            if (Cons.currentNativeAdFacebook != null) {
                                binding?.fbNativeAdContainer?.let {
                                    adManager?.inflateFbNativeAd(
                                        Cons.currentNativeAdFacebook!!, it
                                    )
                                }
                            } else {
                                fbNativeAd = NativeAd(context, nativeFacebook)
                                binding?.adLoadLay2?.visibility = View.VISIBLE
                                binding?.fbNativeAdContainer?.let {
                                    adManager?.loadFacebookNativeAd(
                                        fbNativeAd!!,
                                        it, binding?.adLoadLay2
                                    )
                                }
                            }
                        }
                    }

                }
            } else {
                hideStreaming()
                if (!it.app_ads.isNullOrEmpty()) {

                    checkNativeAdProvider =
                        adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                            .toString()

                    if (checkNativeAdProvider.equals(Constants.admob, true)) {

                        if (Cons.currentNativeAd != null) {
                            binding?.nativeAdView?.let {
                                adManager?.populateNativeAdView(
                                    Cons.currentNativeAd!!,
                                    it
                                )
                            }
                        }else {
                            binding?.adLoadLay?.visibility = View.VISIBLE
                            binding?.nativeAdView?.let {
                                adManager?.loadAdmobNativeAd(
                                    null,
                                    it,
                                    binding?.adLoadLay
                                )
                            }
                        }
                    } else if (checkNativeAdProvider.equals(Constants.facebook, true)) {

                        if (Cons.currentNativeAdFacebook != null) {
                            binding?.fbNativeAdContainer?.let {
                                adManager?.inflateFbNativeAd(
                                    Cons.currentNativeAdFacebook!!, it
                                )
                            }
                        } else {
                            fbNativeAd = NativeAd(context, nativeFacebook)
                            binding?.adLoadLay2?.visibility = View.VISIBLE
                            binding?.fbNativeAdContainer?.let {
                                adManager?.loadFacebookNativeAd(
                                    fbNativeAd!!,
                                    it, binding?.adLoadLay2
                                )
                            }
                        }
                    }
//                    else if (checkNativeAdProvider.equals(cas_Ai, true)){
//                        binding?.nativeAdView?.let {
//                            adManager?.loadNativeAdCasAi(binding?.a)
//                        }
//                    }

                    else {


                    }
                }
            }
        }
    }

    private fun hideStreaming() {

    }

    private fun showStreaming() {

    }


    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {

            val listWithAd: List<LiveScoresModel?> =
                if (checkNativeAdProvider != "none") {
                    checkNativeAd(liveScores)
                } else {
                    liveScores
                }


            val listAdapter = LiveSliderAdapterNative(
                requireContext(), this,
                "main", liveScores, "none", adManager!!
            )

            binding?.recyclerviewSlider?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding?.recyclerviewSlider?.adapter = listAdapter
            listAdapter.submitList(liveScores)

            ////////////////////////////////////////////////
            binding?.viewDots?.attachToRecyclerView(binding!!.recyclerviewSlider)
            if (binding?.recyclerviewSlider?.onFlingListener == null)
                LinearSnapHelper().attachToRecyclerView(binding?.recyclerviewSlider)
            val animator: DefaultItemAnimator = object : DefaultItemAnimator() {
                override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                    return true
                }
            }
            binding?.recyclerviewSlider?.itemAnimator = animator
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setAdapter(liveEvents: MutableList<Event>) {

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

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

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