package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentRecentBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.RecentlMatchesPagerAdapter
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.AllMatchesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.odiFormat
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.t20Format
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.testFormat

class RecentFragment : Fragment() {


    var binding: FragmentRecentBinding? = null
    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[AllMatchesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recent, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        setUpViewPager()

        return view
    }

    private fun setUpViewPager() {
        val recentStatus = resources.getStringArray(R.array.matches_formats)
        val fragmentAdapter = RecentlMatchesPagerAdapter(this, "2")
        binding?.viewPagerRecent?.isUserInputEnabled = true
        binding?.tabsRecent?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRecent?.currentItem = 0
        binding?.viewPagerRecent?.adapter = fragmentAdapter
        binding?.tabsRecent?.let {
            binding?.viewPagerRecent?.let { it1 ->
                TabLayoutMediator(
                    it, it1
                ) { tab: TabLayout.Tab, position: Int ->

                    tab.text = recentStatus[position]
                }.attach()
            }
        }


        binding?.tabsRecent?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.position?.let { RecentMatchesFragment.newInstance(it) }
                when (tab?.position) {
                    0 -> {
                        recentViewModel.isTabSelect.value = t20Format

                    }
                    1 -> {
                        recentViewModel.isTabSelect.value = odiFormat

                    }
                    2 -> {
                        recentViewModel.isTabSelect.value = testFormat

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        recentViewModel.isTabSelect.value = t20Format

                    }
                    1 -> {
                        recentViewModel.isTabSelect.value = odiFormat

                    }
                    2 -> {
                        recentViewModel.isTabSelect.value = testFormat

                    }
                }

            }
        })
    }
}