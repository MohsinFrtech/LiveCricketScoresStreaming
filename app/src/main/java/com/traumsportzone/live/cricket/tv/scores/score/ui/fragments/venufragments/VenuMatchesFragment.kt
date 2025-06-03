package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.venufragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.VenuMatchesLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.VenuMatchesTabsAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.VenuViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData

class VenuMatchesFragment : Fragment(), NavigateData, AdManagerListener {
    private val venuMatchesModel by lazy {
        ViewModelProvider(requireActivity())[VenuViewModel::class.java]
    }
    private var bindingVenuMatchesFragment: VenuMatchesLayoutBinding? = null
    private var adManagerClass: AdManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val venuMatchesLay = inflater.inflate(R.layout.venu_matches_layout, container, false)
        bindingVenuMatchesFragment = DataBindingUtil.bind(venuMatchesLay)
        bindingVenuMatchesFragment?.lifecycleOwner = this
        adManagerClass = AdManager(requireContext(), requireActivity(), this)
        loadParticularVenuMatches()

        return venuMatchesLay
    }

    private fun loadParticularVenuMatches() {
        venuMatchesModel?.loadVenueMatchesFromRemote()
        venuMatchesModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                bindingVenuMatchesFragment?.progressBar?.visibility=View.VISIBLE
            }
            else
            {
                bindingVenuMatchesFragment?.progressBar?.visibility=View.GONE
            }
        })
        setVenuTabs()
    }

    private fun setVenuTabs() {
        val recentStatus = resources.getStringArray(R.array.matches_formats)
        val fragmentAdapter = VenuMatchesTabsAdapter(this)
        bindingVenuMatchesFragment?.viewPagerVenu?.isUserInputEnabled = true
        bindingVenuMatchesFragment?.tabsVenues?.tabGravity = TabLayout.GRAVITY_FILL
        bindingVenuMatchesFragment?.viewPagerVenu?.adapter = fragmentAdapter
        bindingVenuMatchesFragment?.tabsVenues?.let {
            bindingVenuMatchesFragment?.viewPagerVenu?.let { it1 ->
                TabLayoutMediator(
                    it, it1
                ) { tab: TabLayout.Tab, position: Int ->
                    tab.text = recentStatus[position]
                }.attach()
            }
        }
    }

    private fun setUpRecyclerForVenuMatches(venuMatches: List<LiveScoresModel?>) {
//        val listWithAd: List<LiveScoresModel?> =
//            if (AppConstants.nativeAdProviderName != "none") {
//                AppUtils.checkNativeAd(venuMatches)
//            } else {
//                venuMatches
//            }
//        val listAdapter = adManagerClass?.let {
//            LiveScoreNativeAdapter(
//                requireContext(), listWithAd, seriesMatchesFragment, this,
//                AppConstants.nativeAdProviderName, it
//            )
//        }
//        bindingVenuMatchesFragment?.rvVenuMatches?.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        bindingVenuMatchesFragment?.rvVenuMatches?.adapter = listAdapter
//        listAdapter?.submitList(listWithAd)
    }


    override fun navigation(viewId: NavDirections) {
        findNavController().navigate(viewId)
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {
    }

}