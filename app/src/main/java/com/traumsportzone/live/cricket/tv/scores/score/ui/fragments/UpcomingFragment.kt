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
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentUpcomingBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.UpcomingMatchesPagerAdapter
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.UpcomingMatchesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.odiFormat
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.t20Format
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.testFormat

class UpcomingFragment:Fragment() {

    var bindingUpcoming: FragmentUpcomingBinding?=null
    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[UpcomingMatchesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_upcoming,container,false)
        bindingUpcoming= DataBindingUtil.bind(view)
        bindingUpcoming?.lifecycleOwner=this
        setUpViewPager()
        return view
    }


    private fun setUpViewPager() {
        val recentStatus = resources.getStringArray(R.array.matches_formats)
        val fragmentAdapter = UpcomingMatchesPagerAdapter(this, "2")
        bindingUpcoming?.viewPagerRecent?.isUserInputEnabled = true
        bindingUpcoming?.tabsRecent?.tabGravity = TabLayout.GRAVITY_FILL
        bindingUpcoming?.viewPagerRecent?.currentItem=0
        bindingUpcoming?.viewPagerRecent?.adapter = fragmentAdapter
        bindingUpcoming?.tabsRecent?.let {
            bindingUpcoming?.viewPagerRecent?.let { it1 ->
                TabLayoutMediator(it, it1
                ) { tab: TabLayout.Tab, position: Int ->

                    tab.text = recentStatus[position]
                }.attach()
            }
        }


        bindingUpcoming?.tabsRecent?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.position?.let { RecentMatchesFragment.newInstance(it) }
                if (tab?.position==0)
                {
                    recentViewModel.isTabSelect.value= t20Format

                }
                else if (tab?.position==1)
                {
                    recentViewModel.isTabSelect.value= odiFormat

                }
                else if (tab?.position==2)
                {
                    recentViewModel.isTabSelect.value= testFormat

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab?.position==0)
                {
                    recentViewModel.isTabSelect.value= t20Format

                }
                else if (tab?.position==1)
                {
                    recentViewModel.isTabSelect.value= odiFormat

                }
                else if (tab?.position==2)
                {
                    recentViewModel.isTabSelect.value= testFormat

                }

            }
        })
    }

}