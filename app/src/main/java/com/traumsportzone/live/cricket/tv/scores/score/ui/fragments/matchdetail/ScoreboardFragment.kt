package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.ScoreBoardLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.ScoreBoardAdapter
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.CommentaryViewModel

class ScoreboardFragment : Fragment() {

    var binding: ScoreBoardLayoutBinding? = null
    private val scoreBoardViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.score_board_layout, container, false)
        binding = DataBindingUtil.bind(layout)
        getMatchScoreBoard()
        return layout
    }

    //Match Scoreboard
    private fun getMatchScoreBoard() {
        scoreBoardViewModel?.scoreCardModel?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.matchHeader != null) {
                    binding?.matchStatus?.text = it.matchHeader?.status?.toString()
                }

                if (!it.scoreCard.isNullOrEmpty()) {
                    val reverseList = it.scoreCard.reversed()
                    val listAdapter =
                        ScoreBoardAdapter(requireContext())
                    binding?.scoreBoardRecycler?.layoutManager = LinearLayoutManager(
                        requireContext()
                    )
                    binding?.scoreBoardRecycler?.adapter = listAdapter
                    listAdapter.submitList(reverseList)
                }
            }
        })
    }
}