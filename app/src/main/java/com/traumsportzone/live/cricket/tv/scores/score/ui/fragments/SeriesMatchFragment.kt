package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentMatchesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.SeriesMatchesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd

class SeriesMatchFragment : Fragment(), NavigateData, AdManagerListener {


    var bindingMatches: FragmentMatchesBinding? = null
    private val seriesViewModel by lazy {
        ViewModelProvider(requireActivity())[SeriesMatchesViewModel::class.java]
    }

    private var adManager: AdManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        bindingMatches = DataBindingUtil.bind(view)
        bindingMatches?.lifecycleOwner = this
        bindingMatches?.viewmodel = seriesViewModel

        adManager = AdManager(requireContext(), requireActivity(), this)


        setUpViewModel()
        return view
    }

    private fun setUpViewModel() {

        seriesViewModel.againLoad()
        seriesViewModel.sliderList.observe(viewLifecycleOwner)
        {
            if (!it.isNullOrEmpty()) {
                bindingMatches?.tvNoData?.visibility = View.GONE
                setAdapter2(it)
            } else {
                bindingMatches?.tvNoData?.visibility = View.VISIBLE
            }


        }
    }

    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
            //val listAdapter = LiveSliderAdapter(requireContext(), this, "seriesMatch")

            val listWithAd: List<LiveScoresModel?> =
                if (Constants.checkNativeAdProvider != "none") {
                    MyNativeAd.checkNativeAd(liveScores)
                } else {
                    liveScores
                }

            val listAdapter = LiveSliderAdapterNative(
                requireContext(), this,
                "seriesMatch", listWithAd, Constants.checkNativeAdProvider, adManager!!
            )

            bindingMatches?.rvSeries?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindingMatches?.rvSeries?.adapter = listAdapter
            listAdapter.submitList(listWithAd)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {

        findNavController().navigate(viewId)
    }

    override fun onAdLoad(value: String) {
        TODO("Not yet implemented")
    }

    override fun onAdFinish() {
        TODO("Not yet implemented")
    }


}