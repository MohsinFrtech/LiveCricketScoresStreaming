package com.traumsportzone.live.cricket.tv.scores.score.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matches.MatchesCategoryFragment
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons

class MatchesPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchesCategoryFragment.newInstance(Cons.match_category_recent)
            1 -> MatchesCategoryFragment.newInstance(Cons.match_category_live)
            2 -> MatchesCategoryFragment.newInstance(Cons.match_category_upcoming)
            else -> {
                MatchesCategoryFragment.newInstance(Cons.match_category_recent)
            }
        }
    }

}