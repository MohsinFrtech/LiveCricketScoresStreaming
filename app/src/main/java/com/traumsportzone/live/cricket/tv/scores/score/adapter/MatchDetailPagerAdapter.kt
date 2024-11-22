package com.traumsportzone.live.cricket.tv.scores.score.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail.CommentaryFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail.MatchDetailFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail.NewsFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail.ScoreboardFragment
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail.SquadFragment

class MatchDetailPagerAdapter(fm: Fragment, status: LiveScoresModel?): FragmentStateAdapter(fm) {
    var model: LiveScoresModel? = null

    init {
        model = status
    }


    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CommentaryFragment()
        1 -> MatchDetailFragment.newInstance(model)
        2 -> ScoreboardFragment()
        3->SquadFragment()
        else -> NewsFragment()
    }
}