package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentRankingMatchesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.MatchesPagerAdapter
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.MatchesCategoryViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import com.traumsportzone.live.cricket.tv.scores.utils.Logger

class MatchesFragment : Fragment(), ApiResponseListener {

    private var binding: FragmentRankingMatchesBinding? = null
    private val tags = "MatchesFragment"
    private val logger = Logger()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MatchesCategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ranking_matches, container, false)
        binding = DataBindingUtil.bind(view)

        binding?.viewModel = viewModel
        viewModel.apiResponseListener = this


        val args: MatchesFragmentArgs by navArgs()

        val teamId = args.teamId
        val teamName = args.teamName

        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }


        if (InternetUtil.isInternetOn(requireContext())) {
            viewModel.loadMatchesList(teamId)
        }


        viewModel.matchesList.observe(viewLifecycleOwner) {
            if (it != null) {
                setUpViewPager()
            }

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.rotateloadingMatches?.visibility = View.VISIBLE
            } else {
                binding?.rotateloadingMatches?.visibility = View.GONE

            }
        }

        if (!teamName.isNullOrEmpty()) {
            binding?.tvTitleMatch?.text = teamName
        }

        return view
    }

    private fun setUpViewPager() {
        val array = resources.getStringArray(R.array.matches_status)
        val fragmentAdapter = MatchesPagerAdapter(childFragmentManager, this.lifecycle)
        binding?.viewPagerMatches?.isUserInputEnabled = true
        binding?.tabsMatches?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerMatches?.adapter = fragmentAdapter
        binding?.tabsMatches.let {
            binding?.viewPagerMatches.let { it1 ->
                if (it != null) {
                    if (it1 != null) {
                        TabLayoutMediator(
                            it, it1
                        ) { tab: TabLayout.Tab, position: Int ->
                            tab.text = array[position]
                        }.attach()
                    }
                }
            }
        }


        binding?.tabsMatches?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.isTabSelect.value= Cons.match_category_recent

                    }
                    1 -> {
                        viewModel.isTabSelect.value= Cons.match_category_live

                    }
                    2 -> {
                        viewModel.isTabSelect.value= Cons.match_category_upcoming

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("TabSelection", "value" + tab?.position)

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.isTabSelect.value= Cons.match_category_recent

                    }
                    1 -> {
                        viewModel.isTabSelect.value= Cons.match_category_live

                    }
                    2 -> {
                        viewModel.isTabSelect.value= Cons.match_category_upcoming

                    }
                }
            }
        })
    }


    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onFailure(message: String) {
    }
}