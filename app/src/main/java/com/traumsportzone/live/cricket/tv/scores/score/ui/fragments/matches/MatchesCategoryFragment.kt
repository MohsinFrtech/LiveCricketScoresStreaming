package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matches

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
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentMatchesCategoryBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.MatchesCategoryViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.MyNativeAd


class MatchesCategoryFragment : Fragment(), NavigateData, AdManagerListener {

    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[MatchesCategoryViewModel::class.java]
    }

    val liveScores: MutableList<LiveScoresModel> = ArrayList<LiveScoresModel>()
    private var tabSelect = ""
    var binding: FragmentMatchesCategoryBinding? = null

    private var adManager: AdManager? = null


    companion object {
        fun newInstance(state: String): MatchesCategoryFragment {
            val matchesFragment = MatchesCategoryFragment()
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

        val view = inflater.inflate(R.layout.fragment_matches_category, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.viewModel = recentViewModel

        adManager = AdManager(requireContext(), requireActivity(), this)


        setUpViewModel()

        return binding?.root
    }

    private fun setUpViewModel() {

        recentViewModel.isTabSelect.observe(this.viewLifecycleOwner)
        {


            showFilteredList(it)

        }
    }

    private fun showFilteredList(s: String) {


        recentViewModel.isLoading.observe(this.viewLifecycleOwner)
        {

            if (it) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }


        }

        recentViewModel.matchesList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty()) {
                for (match in it) {

                    if (match?.state.equals(s, true)) {
                        match?.let { it1 -> liveScores.add(it1) }
                    }
                }
                if (liveScores.isNotEmpty()) {
                    binding?.tvNoData?.visibility = View.GONE
                    binding?.rvMatches?.visibility = View.VISIBLE
                    setAdapter2(liveScores)
                } else {
                    binding?.tvNoData?.visibility = View.VISIBLE
                    binding?.rvMatches?.visibility = View.GONE
                }
            }


        }
    }


    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
//            binding?.progressBar?.visibility=View.GONE
            //val listAdapter = LiveSliderAdapter(requireContext(), this, "recent")
            var tFormatList: MutableList<LiveScoresModel?> = liveScores.toMutableList()
            if(!tFormatList.isNullOrEmpty()) {
                tFormatList.sortBy {
                    it?.id
                }


                val listWithAd: List<LiveScoresModel?> =
                    if (Constants.checkNativeAdProvider != "none") {
                        MyNativeAd.checkNativeAd(tFormatList)
                    } else {
                        tFormatList
                    }

                val listAdapter = LiveSliderAdapterNative(
                    requireContext(), this,
                    "recent", listWithAd, Constants.checkNativeAdProvider, adManager!!
                )
                binding?.rvMatches?.visibility=View.VISIBLE

                binding?.rvMatches?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
        TODO("Not yet implemented")
    }

    override fun onAdFinish() {
        TODO("Not yet implemented")
    }


}