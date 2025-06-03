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
import com.traumsportzone.live.cricket.tv.scores.databinding.VenuScreenBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.VenuAdapter
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.NavigateData
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.VenuViewModel

class VenuFragment : Fragment(), NavigateData {
    private var bindingVenu: VenuScreenBinding? = null

    private val browseVenuModel by lazy {
        ViewModelProvider(requireActivity())[VenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val venuLay = inflater.inflate(R.layout.venu_screen, container, false)
        bindingVenu = DataBindingUtil.bind(venuLay)
        bindingVenu?.lifecycleOwner = this
        setUpVenusData()
        bindingVenu?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }
        return venuLay
    }

    private fun setUpVenusData() {
        browseVenuModel?.againLoad()

        browseVenuModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                bindingVenu?.progressBar?.visibility = View.VISIBLE
            } else {
                bindingVenu?.progressBar?.visibility = View.GONE
            }
        })

        browseVenuModel?.venuList?.observe(viewLifecycleOwner, Observer {

            val listAdapter = VenuAdapter(it, this)
            bindingVenu?.recyclerViewVenues?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindingVenu?.recyclerViewVenues?.adapter = listAdapter
            listAdapter.submitList(it)
        })
    }

    override fun navigation(viewId: NavDirections) {
        findNavController().navigate(viewId)
    }
}