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
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.VenuMatchesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.LiveSliderAdapterNative
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.NavigateData
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.VenuViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.visibility
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class VenuT20Fragment:Fragment(), AdManagerListener, com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData {
    private val venuT20MatchesModel by lazy {
        ViewModelProvider(requireActivity())[VenuViewModel::class.java]
    }

    private var bindingVenuMatchesFragment: VenuMatchesBinding? = null
    private var adManagerClass: AdManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutT20 = inflater.inflate(R.layout.venu_matches,container,false)
        bindingVenuMatchesFragment = DataBindingUtil.bind(layoutT20)
        adManagerClass = AdManager(requireContext(), requireActivity(), this)
        setT20DataForVenuMatches()
        return layoutT20
    }


    private fun setT20DataForVenuMatches() {
        venuT20MatchesModel.venuMatchesList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                bindingVenuMatchesFragment?.noResult?.visibility(false)
                setRecyclerForT20(it)
            } else {
                bindingVenuMatchesFragment?.noResult?.text = resources.getString(R.string.noVenuT20)
                bindingVenuMatchesFragment?.noResult?.visibility(true)

            }
        })
    }

    //Setup recycler for t20 venu matches....
    private fun setRecyclerForT20(liveScoresModels: List<LiveScoresModel?>) {
        val t20FormatList: MutableList<LiveScoresModel> =
            ArrayList<LiveScoresModel>()
        liveScoresModels.forEach {
            if (it?.match_format?.equals(Cons.match_format_t20, true) == true) {
                t20FormatList.add(it)
            }
        }

        val listWithAd: List<LiveScoresModel?> =
            if (Constants.nativeAdProvider != "none") {
                CodeUtils.checkNativeAd(t20FormatList)
            } else {
                t20FormatList
            }
        val listAdapter = adManagerClass?.let {
            LiveSliderAdapterNative(
                requireContext(), this, "venu", listWithAd,
                Constants.nativeAdProvider, it
            )
        }
        bindingVenuMatchesFragment?.venuMatchesRecycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bindingVenuMatchesFragment?.venuMatchesRecycler?.adapter = listAdapter
        listAdapter?.submitList(listWithAd)
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }

    override fun navigation(viewId: NavDirections) {
        findNavController().navigate(viewId)
    }


}