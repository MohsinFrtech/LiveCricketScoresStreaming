package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.os.Bundle
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
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentSeriesBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.SeriesItemAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.SeriesScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.SeriesViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData

class BrowseFragment:Fragment(), NavigateData {


    private var bindingSeries: FragmentSeriesBinding?=null
    private val seriesViewModel by lazy {
        ViewModelProvider(requireActivity())[SeriesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_series,container,false)
        bindingSeries=DataBindingUtil.bind(view)
        bindingSeries?.lifecycleOwner=this
        bindingSeries?.viewmodel=seriesViewModel
        setUpViewModel()

        bindingSeries?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    private fun setUpViewModel() {
        seriesViewModel.sliderList.observe(viewLifecycleOwner)
        {

            if (!it.isNullOrEmpty())
            {
                bindingSeries?.tvNoData?.visibility=View.GONE
                setAdapter(it)
            }
            else
            {
                bindingSeries?.tvNoData?.visibility=View.VISIBLE
            }

        }
    }


    private fun setAdapter(liveScores: List<SeriesScoresModel?>) {
        try {
            val listAdapter = SeriesItemAdapter(requireContext(),this,"recent")
            bindingSeries?.rvSeries?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindingSeries?.rvSeries?.adapter = listAdapter
            listAdapter.submitList(liveScores)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {

        if (viewId!=null)
        {
            findNavController().navigate(viewId)
        }
    }

}