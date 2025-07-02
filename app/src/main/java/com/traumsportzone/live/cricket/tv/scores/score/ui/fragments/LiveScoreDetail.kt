package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentLiveDetailBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.MatchDetailPagerAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.CommentaryViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.DialogListener
import com.traumsportzone.live.cricket.tv.scores.utils.Logger


class LiveScoreDetail : Fragment(), AdManagerListener, ApiResponseListener, DialogListener {

    private var binding: FragmentLiveDetailBinding? = null
    private val tags = "LiveScoreDetail"
    private val logger = Logger()
    private val commentaryViewModel by lazy {
        ViewModelProvider(this)[CommentaryViewModel::class.java]
    }
    private var liveScoresModel: LiveScoresModel? = null
    private var adManager: AdManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_live_detail, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        val liveData: LiveScoreDetailArgs by navArgs()
        liveScoresModel = liveData.getDetails
        binding?.model = liveScoresModel
        commentaryViewModel.apiResponseListener = this
        //load banner Ad
        adManager = AdManager(requireContext(), requireActivity(), this)
//        if (adLocation1Provider != "none") {
//            binding?.adView?.let { it1 ->
//                binding?.fbAdView?.let { it2 ->
//                    binding?.startAppBanner?.let { it3 ->
//                        adManager?.loadAdProvider(
//                            adLocation1Provider, Constants.adLocation1,
//                            it1, it2, binding?.unityBannerView, it3
//                        )
//                    }
//                }
//            }
//        }


        binding?.backIcon?.setOnClickListener {
            findNavController().popBackStack()
        }
        setUpViewPager()
        return view
    }

    private fun setUpViewPager() {
        val recentStatus = resources.getStringArray(R.array.matches_details)
        val fragmentAdapter = MatchDetailPagerAdapter(this, liveScoresModel)
        binding?.viewPagerRecent?.isUserInputEnabled = true
        binding?.tabsLiveDetail?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRecent?.currentItem = 0
        binding?.viewPagerRecent?.adapter = fragmentAdapter
        binding?.tabsLiveDetail?.let {
            binding?.viewPagerRecent?.let { it1 ->
                TabLayoutMediator(
                    it, it1
                ) { tab: TabLayout.Tab, position: Int ->

                    tab.text = recentStatus[position]
                }.attach()
            }
        }


        binding?.tabsLiveDetail?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
//
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {


            }
        })
    }




    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
//        binding?.recentOvs?.visibility=View.GONE
//        binding?.liveMatchDetail?.visibility=View.GONE
//        binding?.recyclerViewCommentary?.visibility=View.GONE
//        binding?.ketStatResult?.visibility=View.GONE
//        binding?.playerOftheMatch?.visibility=View.GONE
//        binding?.playerOftheMatchName?.visibility=View.GONE
//        CustomDialogue(this).showDialog(
//            requireContext(), "title", message,
//            "", "Exit", "eventValue"
//        )
    }

    override fun onPositiveDialogText(key: String) {

    }

    override fun onNegativeDialogText(key: String) {

    }
}