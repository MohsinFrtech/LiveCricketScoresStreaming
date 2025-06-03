package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.PlayerRankingDetailBinding
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.PlayersRankingViewModel

class PlayerRankingDetail:Fragment() {

    private var playerRankingDetailBinding: PlayerRankingDetailBinding?= null
    private val playerRankingViewModel by lazy {
        ViewModelProvider(requireActivity())[PlayersRankingViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.player_ranking_detail,container,false)
        playerRankingDetailBinding = DataBindingUtil.bind(view)
        playerRankingDetailBinding?.lifecycleOwner = this
        getPlayerRankingDetail()
        return view
    }

    private fun getPlayerRankingDetail() {
        playerRankingViewModel?.getPlayerRankingDetail()
        playerRankingViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                playerRankingDetailBinding?.progressBar?.visibility=View.VISIBLE
            }
            else
            {
                playerRankingDetailBinding?.progressBar?.visibility=View.GONE
            }
        })

        playerRankingViewModel?.playerRankingDetail?.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                playerRankingDetailBinding?.personName?.visibility = View.VISIBLE
                playerRankingDetailBinding?.personRoleAndTeam?.visibility = View.VISIBLE
                playerRankingDetailBinding?.matchDetailLay?.visibility = View.VISIBLE
                playerRankingDetailBinding?.personBio?.visibility = View.VISIBLE

                playerRankingDetailBinding?.viewModel = it
                playerRankingDetailBinding?.personName?.text = it.name.toString()
                playerRankingDetailBinding?.personRoleAndTeam?.text = it.intlTeam.toString() +" ︱"+ it.role.toString()
            }
            else{
                playerRankingDetailBinding?.personName?.visibility = View.GONE
                playerRankingDetailBinding?.personRoleAndTeam?.visibility = View.GONE
                playerRankingDetailBinding?.matchDetailLay?.visibility = View.GONE
                playerRankingDetailBinding?.personBio?.visibility = View.GONE
            }
        })

    }
}