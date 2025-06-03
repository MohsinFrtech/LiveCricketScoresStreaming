package com.traumsportzone.live.cricket.tv.scores.score.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.venufragments.VenuOdiFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.venufragments.VenuT20Fragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.venufragments.VenuTestFragment

class VenuMatchesTabsAdapter (fm: Fragment): FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> VenuT20Fragment()
        1 -> VenuOdiFragment()
        else -> VenuTestFragment()
    }
}