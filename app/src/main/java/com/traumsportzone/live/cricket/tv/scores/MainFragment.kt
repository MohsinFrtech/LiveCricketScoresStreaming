package com.traumsportzone.live.cricket.tv.scores

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
import androidx.recyclerview.widget.*
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentEventBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.LiveViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adapters.EventAdapter
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Event
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.adLocation1Provider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.checkNativeAdProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd.checkNativeAd
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil

class MainFragment : Fragment(), NavigateData, AdManagerListener {


    var binding: FragmentEventBinding? = null
    private val modelEvent by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var adProviderName = "none"
    private var adManager: AdManager? = null
    private var liveChannelCount = 0
    private var navDirections: NavDirections? = null
    private var adStatus = false
    private val liveViewModel by lazy {
        ViewModelProvider(requireActivity())[LiveViewModel::class.java]
    }

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

    }

    private fun setUpViewModel() {

        // Observe Live Data for updating Data in slider
        liveViewModel.sliderList.observe(viewLifecycleOwner) {
            if (it != null) {

                setAdapter2(it)
            }
        }
        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {

            adStatus = false
            if (it.live == true) {

                if (!it.events.isNullOrEmpty()) {

                    Constants.middleAdProvider = "none"


                    liveChannelCount = 0

                    val liveEvents: MutableList<Event> =
                        ArrayList<Event>()

                    if (!it.app_ads.isNullOrEmpty()) {

                        checkNativeAdProvider =
                            adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                                .toString()

                        adProviderName =
                            adManager?.checkProvider(it.app_ads!!, Constants.adMiddle).toString()
                        Constants.middleAdProvider = adProviderName
                        if (!adProviderName.equals(Constants.startApp, true)) {
                            adManager?.loadAdProvider(
                                adProviderName, Constants.adMiddle,
                                null, null, null, null
                            )

                        }


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

                        } else if (checkNativeAdProvider.equals(Constants.facebook, true)) {

                            if (Cons.currentNativeAdFacebook != null) {
                                binding?.fbNativeAdContainer?.let {
                                    adManager?.inflateFbNativeAd(
                                        Cons.currentNativeAdFacebook!!, it
                                    )
                                }
                            }
                        }

                    }



                    for (event in it.events!!) {
                        if (event.live == true) {
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


                        if (checkNativeAdProvider.equals(Constants.admob, true)) {
                            if (Cons.currentNativeAd != null) {
                                binding?.nativeAdView?.let {
                                    adManager?.populateNativeAdView(
                                        Cons.currentNativeAd!!,
                                        it
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
                        }
                    } else if (checkNativeAdProvider.equals(Constants.facebook, true)) {

                        if (Cons.currentNativeAdFacebook != null) {
                            binding?.fbNativeAdContainer?.let {
                                adManager?.inflateFbNativeAd(
                                    Cons.currentNativeAdFacebook!!, it
                                )
                            }
                        }
                    } else {


                    }
                }
            }
        }
    }

    private fun hideStreaming() {
        binding?.gameRecycler?.visibility = View.GONE
//        binding?.nativeAdView?.visibility = View.VISIBLE
//        binding?.fbNativeAdContainer?.visibility = View.VISIBLE
    }

    private fun showStreaming() {
        binding?.gameRecycler?.visibility = View.VISIBLE
//        binding?.nativeAdView?.visibility = View.GONE
//        binding?.fbNativeAdContainer?.visibility = View.GONE
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
                "main", listWithAd, checkNativeAdProvider, adManager!!
            )

            binding?.recyclerviewSlider?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding?.recyclerviewSlider?.adapter = listAdapter
            listAdapter.submitList(listWithAd)

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
            navDirections = viewId
            if (adStatus) {
                if (!adProviderName.equals("none", true)) {
                    adManager?.showAds(adProviderName)
                } else {
                    findNavController().navigate(viewId)
                }
            } else {
                findNavController().navigate(viewId)
            }
        } catch (e: Exception) {
            Log.d("Exception", "message")
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {

        if (navDirections != null) {
            findNavController().navigate(navDirections!!)
        }
    }


}