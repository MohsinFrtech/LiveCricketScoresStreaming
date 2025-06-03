package com.traumsportzone.live.cricket.tv.scores.streaming.adapters

import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.media3.exoplayer.ExoPlayer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments.LanguageResolutionFragment
import com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments.VideoResolutionFragment

class ResolutionTabsAdapter(fm: Fragment, dialog: Dialog?, playerInstance: ExoPlayer): FragmentStateAdapter(fm) {

    val dialog_instan= dialog
    val exo_play = playerInstance
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> VideoResolutionFragment(dialog_instan,exo_play)
        1 -> LanguageResolutionFragment(dialog_instan,exo_play)
        else -> VideoResolutionFragment(dialog_instan, exo_play)
    }
}