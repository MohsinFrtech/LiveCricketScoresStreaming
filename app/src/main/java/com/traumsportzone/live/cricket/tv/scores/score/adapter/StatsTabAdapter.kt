package com.traumsportzone.live.cricket.tv.scores.score.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment.OdiStatsFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment.T20StatsFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment.TestStatsFragment

class StatsTabAdapter(fm:Fragment) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> T20StatsFragment()
        1 -> OdiStatsFragment()
        else -> TestStatsFragment()
    }

}