package com.traumsportzone.live.cricket.tv.scores.score.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking.AllRounderRankingFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking.BatsManRankingFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking.BowlerRankingFragment

class RankingsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BatsManRankingFragment()
            1 -> BowlerRankingFragment()
            2 -> AllRounderRankingFragment()
            else -> {
                BatsManRankingFragment()
            }
        }
    }

}