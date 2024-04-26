package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.UpcomingFragmentMatchesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.UpcomingMatchesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.checkNativeAdProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd

class UpcomingMatchesFragment : Fragment(), NavigateData, AdManagerListener {

    private val upcomingViewModel by lazy {
        ViewModelProvider(requireActivity())[UpcomingMatchesViewModel::class.java]
    }

    val liveScores: MutableList<LiveScoresModel> =
        ArrayList<LiveScoresModel>()

    private var adManager: AdManager? = null

    companion object {
        fun newInstance(state: String): UpcomingMatchesFragment {
            val matchesFragment = UpcomingMatchesFragment()
            val bundle = Bundle()
            bundle.putString("Fragment_format", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }

    var bindingUpcomingMatches: UpcomingFragmentMatchesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.upcoming_fragment_matches, container, false)
        bindingUpcomingMatches = DataBindingUtil.bind(view)
        bindingUpcomingMatches?.lifecycleOwner = this
        bindingUpcomingMatches?.viewModel = upcomingViewModel

        adManager =  AdManager(requireContext(), requireActivity(), this)

        setUpViewModel()

        return view
    }


    private fun setUpViewModel() {
        upcomingViewModel.isTabSelect.observe(viewLifecycleOwner)
        {


            showFilteredList(it)

        }
    }

    private fun showFilteredList(s: String) {


        upcomingViewModel.isLoading.observe(viewLifecycleOwner)
        {
            if (it) {
                bindingUpcomingMatches?.progressBar?.visibility = View.VISIBLE
            } else {
                bindingUpcomingMatches?.progressBar?.visibility = View.GONE
            }


        }

        upcomingViewModel.sliderList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty()) {
                bindingUpcomingMatches?.tvNoData?.visibility = View.GONE

                for (match in it) {

                    if (match?.match_format.equals(s, true)) {
                        match?.let { it1 -> liveScores.add(it1) }
                    }

                }
                setAdapter2(liveScores)
            } else {
                bindingUpcomingMatches?.tvNoData?.visibility = View.VISIBLE

            }


        }
    }


    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
//            binding?.progressBar?.visibility=View.GONE
            //val listAdapter = LiveSliderAdapter(requireContext(),this,"recent")

            val listWithAd: List<LiveScoresModel?> =
                if (checkNativeAdProvider != "none") {
                    MyNativeAd.checkNativeAd(liveScores)
                } else {
                    liveScores
                }

            val listAdapter = LiveSliderAdapterNative(
                requireContext(), this,
                "recent", listWithAd, checkNativeAdProvider, adManager!!
            )

            bindingUpcomingMatches?.rvMatches?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindingUpcomingMatches?.rvMatches?.adapter = listAdapter
            listAdapter.submitList(listWithAd)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {

    }

    override fun onAdLoad(value: String) {
        TODO("Not yet implemented")
    }

    override fun onAdFinish() {
        TODO("Not yet implemented")
    }
}