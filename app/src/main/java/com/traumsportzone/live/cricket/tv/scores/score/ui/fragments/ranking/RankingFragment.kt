package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentRankingBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.RankingsPagerAdapter
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.PlayersRankingViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RankingFragment : Fragment(), ApiResponseListener {

    private var binding: FragmentRankingBinding? = null
    private val tags = "RankingFragment"
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[PlayersRankingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        binding = DataBindingUtil.bind(view)

        binding?.viewModel = viewModel
        viewModel.apiResponseListener = this

        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()

        observeData()
    }

    private fun loadData() {
        this.lifecycleScope.launch(Dispatchers.IO) {
            if (InternetUtil.isInternetOn(requireContext())) {
                ////////////   call players ranking Api  ///////////
                viewModel.getT20Ranking()
                viewModel.getTestRanking()
                viewModel.getODIRanking()
            }
        }
    }

    private fun observeData() {
        viewModel.teamsOdiList.observe(viewLifecycleOwner) {
            if (it != null) {
                setUpViewPager()
            }else{
                loadData()
            }

        }
    }

    private fun setUpViewPager() {
        val rankingsArray = resources.getStringArray(R.array.browse_rankings)
        val fragmentAdapter = RankingsPagerAdapter(childFragmentManager, this.lifecycle)
        binding?.viewPagerRanking?.isUserInputEnabled = true
        binding?.tabsRanking?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRanking?.adapter = fragmentAdapter
        binding?.tabsRanking.let {
            binding?.viewPagerRanking.let { it1 ->
                if (it != null) {
                    if (it1 != null) {
                        TabLayoutMediator(
                            it, it1
                        ) { tab: TabLayout.Tab, position: Int ->
                            tab.text = rankingsArray[position]
                        }.attach()
                    }
                }
            }
        }

        hideLoading()

        binding?.tabsRanking?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


    override fun onStarted() {
    }

    override fun onSuccess() {
        //observeData()
    }

    override fun onFailure(message: String) {
    }

    private fun showLoading() {
        binding?.homeAnimLayout?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.homeAnimLayout?.visibility = View.GONE
    }

}