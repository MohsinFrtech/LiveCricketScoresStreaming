package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentRecentMatchesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.AllMatchesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.checkNativeAdProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd.checkNativeAd
import java.util.ArrayList


class RecentMatchesFragment : Fragment(), NavigateData, AdManagerListener {

    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[AllMatchesViewModel::class.java]
    }

    private val liveScores: MutableList<LiveScoresModel> =
        ArrayList<LiveScoresModel>()
    private var tabSelect = ""
    var binding: FragmentRecentMatchesBinding? = null

    private var adManager: AdManager? = null


    companion object {
        fun newInstance(state: String): RecentMatchesFragment {
            val matchesFragment = RecentMatchesFragment()
            val bundle = Bundle()
            bundle.putString("Fragment_format", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments != null) {
            tabSelect = requireArguments().getString("Fragment_format", "abc")

        }

        val view = inflater.inflate(R.layout.fragment_recent_matches, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.viewModel = recentViewModel

        adManager =  AdManager(requireContext(), requireActivity(), this)


        setUpViewModel()

        return binding?.root
    }

    private fun setUpViewModel() {

        recentViewModel.isTabSelect.observe(viewLifecycleOwner)
        {
            showFilteredList(it)
        }
    }

    private fun showFilteredList(s: String) {


        recentViewModel.isLoading.observe(viewLifecycleOwner)
        {

            if (it) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }


        }

        recentViewModel.sliderList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty()) {
                binding?.tvNoData?.visibility = View.GONE

                for (match in it) {

                    if (match?.match_format.equals(s, true)) {
                        match?.let { it1 -> liveScores.add(it1) }
                    }

                }
                if(!liveScores.isNullOrEmpty()){

                }
                else{
                    binding?.tvNoData?.visibility = View.VISIBLE
                }
                setAdapter2(liveScores)
            } else {
                binding?.tvNoData?.visibility = View.VISIBLE
            }


        }
    }


    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
            var tFormatList: MutableList<LiveScoresModel?> = liveScores.toMutableList()
            if(!tFormatList.isNullOrEmpty())
            {
                tFormatList.sortBy {
                    it?.id
                }
                val listWithAd: List<LiveScoresModel?> =
                    if (checkNativeAdProvider != "none") {
                        checkNativeAd(tFormatList)
                    } else {
                        tFormatList
                    }



                val listAdapter = LiveSliderAdapterNative(
                    requireContext(), this,
                    "recent", listWithAd, checkNativeAdProvider, adManager!!
                )
                binding?.rvMatches?.visibility=View.VISIBLE


                binding?.rvMatches?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.rvMatches?.adapter = listAdapter
                listAdapter.submitList(listWithAd)
            }
            else
            {
                binding?.rvMatches?.visibility=View.GONE
                binding?.tvNoData?.visibility = View.VISIBLE

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {
        try {
            findNavController().navigate(viewId)
        } catch (e: Exception) {
            Log.d("Exception", "message")
        }
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }


}