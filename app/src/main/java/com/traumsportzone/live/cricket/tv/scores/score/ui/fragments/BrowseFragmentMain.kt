package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentBrowseBinding
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.PlayersRankingViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class BrowseFragmentMain : Fragment(), AdManagerListener {

    var binding: FragmentBrowseBinding? = null
    private var adManager: AdManager? = null
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[PlayersRankingViewModel::class.java]
    }

    private var isFirst: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_browse, container, false)
        binding = DataBindingUtil.bind(view)

        adManager = AdManager(requireContext(), requireActivity(), this)


        binding?.playerRanking?.setOnClickListener {
            findNavController().navigate(BrowseFragmentMainDirections.actionBrowseToRanking())
        }
        binding?.teamRanking?.setOnClickListener {
            findNavController().navigate(BrowseFragmentMainDirections.actionBrowseToTeamRanking())
        }
        binding?.browseSeries?.setOnClickListener {
            findNavController().navigate(BrowseFragmentMainDirections.actionBrowseFragmentToSeriesFragment())
        }

        binding?.backLatest?.setOnClickListener {
            findNavController()?.popBackStack()
        }
        binding?.browseTeams?.setOnClickListener {
            findNavController().navigate(BrowseFragmentMainDirections.actionBrowseFragmentToTeamFragment())
        }

        if (Constants.checkNativeAdProvider != "none") {
            if (Constants.checkNativeAdProvider.equals(Constants.admob, true)) {
                if (Cons.currentNativeAd != null) {
                    binding?.nativeAdView?.let {
                        adManager?.populateNativeAdView(
                            Cons.currentNativeAd!!,
                            it
                        )
                    }
                }
            } else if (Constants.checkNativeAdProvider.equals(Constants.facebook, true)) {
                if (Cons.currentNativeAdFacebook != null) {
                    binding?.fbNativeAdContainer?.let {
                        adManager?.inflateFbNativeAd(
                            Cons.currentNativeAdFacebook!!, it
                        )
                    }
                }
            }
        }


        return view
    }

    override fun onAdLoad(value: String) {
        TODO("Not yet implemented")
    }

    override fun onAdFinish() {
        TODO("Not yet implemented")
    }
}