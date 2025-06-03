package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.InsideStatLayBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsTabAdapter
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.StatsViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestAvg
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestAvgstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestScorestattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestSrstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestscore
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.higheststrikerate
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFiftiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFoursstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostHundered
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostHundredsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostNinetiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostRunsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostSixesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostWicketstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostfifties
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostfour
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostruns
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostsix
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostwickets
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.statTypeId

class InsideStatFragment:Fragment() {


    var insideStatFragmentBinding:InsideStatLayBinding?=null
    private val statsViewModel by lazy {
        ViewModelProvider(requireActivity())[StatsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layStats = inflater.inflate(R.layout.inside_stat_lay,container,false)
        insideStatFragmentBinding = DataBindingUtil.bind(layStats)
        setStatsTabs()
        insideStatFragmentBinding?.backarrow?.setOnClickListener {
            findNavController()?.popBackStack()
        }
        if(statTypeId == mostWicketstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostwickets

            statsViewModel.loadMostWickets()
            statsViewModel.loadMostT20Wickets()
            statsViewModel.loadMostTestWickets()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == mostRunsstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostruns
            statsViewModel.loadMostODIRuns()
            statsViewModel.loadMostT20Runs()
            statsViewModel.loadMostTestRuns()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == highestScorestattypeid){

            insideStatFragmentBinding?.headertitle?.text = highestscore
            statsViewModel.loadTestHighestScore()
            statsViewModel.loadODIHighestScore()
            statsViewModel.loadT20HighestScore()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == highestSrstattypeid){
            insideStatFragmentBinding?.headertitle?.text = higheststrikerate
            statsViewModel.loadODIHighestSr()
            statsViewModel.loadTestHighestSr()
            statsViewModel.loadT20HighestSr()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        } else if (statTypeId == mostHundredsstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostHundered
            statsViewModel.loadODIMostHundered()
            statsViewModel.loadTestMostHundered()
            statsViewModel.loadT20MostHundered()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == mostFiftiesstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostfifties
            statsViewModel.loadODIMostFifties()
            statsViewModel.loadT20MostFifties()
            statsViewModel.loadTestMostFifties()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == mostSixesstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostsix
            statsViewModel.loadODIMostSixes()
            statsViewModel.loadT20MostSixes()
            statsViewModel.loadTestMostSixes()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == mostFoursstattypeid){

            insideStatFragmentBinding?.headertitle?.text = mostfour
            statsViewModel.loadODIMostFours()
            statsViewModel.loadT20MostFours()
            statsViewModel.loadTestMostFours()
            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == highestAvgstattypeid){

            insideStatFragmentBinding?.headertitle?.text = highestAvg
            statsViewModel.loadODIHighestAvg()
            statsViewModel.loadT20HighestAvg()
            statsViewModel.loadTestHighestAvg()

            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }else if (statTypeId == mostNinetiesstattypeid){

            insideStatFragmentBinding?.headertitle?.text = higheststrikerate
            statsViewModel.loadODIMostNinties()
            statsViewModel.loadT20MostNinties()
            statsViewModel.loadTestMostNinties()

            statsViewModel.isLoadingT20.observe(viewLifecycleOwner, Observer {
                if (it)
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
                else
                {
                    insideStatFragmentBinding?.progressBar?.visibility=View.GONE
                }
            })
        }

        return layStats
    }

    private fun setStatsTabs() {
        val recentStatus = resources.getStringArray(R.array.matches_formats)
        val fragmentAdapter = StatsTabAdapter(this)
        insideStatFragmentBinding?.viewPagerStats?.isUserInputEnabled = true
        insideStatFragmentBinding?.tabsStats?.tabGravity = TabLayout.GRAVITY_FILL
        insideStatFragmentBinding?.viewPagerStats?.adapter = fragmentAdapter
        insideStatFragmentBinding?.tabsStats?.let {
            insideStatFragmentBinding?.viewPagerStats?.let { it1 ->
                TabLayoutMediator(
                    it, it1,
                    TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                        tab.text = recentStatus[position]
                    }).attach()
            }
        }
    }

}