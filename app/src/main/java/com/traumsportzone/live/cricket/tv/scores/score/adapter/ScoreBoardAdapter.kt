package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ScoreboardItemBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.ScoreCardMatch


class ScoreBoardAdapter(private val context: Context) :
    ListAdapter<ScoreCardMatch, ScoreBoardAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ScoreboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(matchScoreCard: ScoreCardMatch, contextScore: Context) {

            if (matchScoreCard != null) {
                var inning_id = ""
                if (matchScoreCard.inningsId == 1) {
                    inning_id = "1st"
                } else if (matchScoreCard.inningsId == 2) {
                    inning_id = "1st"
                } else if (matchScoreCard.inningsId == 3) {
                    inning_id = "2nd"
                } else if (matchScoreCard.inningsId == 4) {
                    inning_id = "2nd"
                }


                binding?.teamInning?.text =
                    matchScoreCard?.batTeamName?.toString() + " " + inning_id.toString() + " Inning"
                if (matchScoreCard.scoreDetails != null) {
                    binding?.teamScore?.text = matchScoreCard.score.toString() + "-" +
                            matchScoreCard.wickets.toString() + " " + "(" + matchScoreCard
                        .overs.toString() + " Ov" + ")"
                }
                //if batting team details are available....
                if (!matchScoreCard.batsman.isNullOrEmpty()) {
                    // if batting team  array is not empty...
                    val newList = matchScoreCard.batsman.filter {
                        it.balls != null && it.balls!! > 0
                    }
                    val listAdapter =
                        BatStatsAdapter(contextScore)
                    binding?.battingDataRecycler?.layoutManager =
                        LinearLayoutManager(contextScore)
                    binding?.battingDataRecycler?.adapter = listAdapter
                    listAdapter.submitList(newList)
                }

                //for setting extra data...
                if (matchScoreCard.extras != null) {
                    if (matchScoreCard.extras!!.total != null) {
                        if (matchScoreCard.extras!!.total!! > 0) {
                            binding?.extraRecord?.visibility = View.VISIBLE
                            ///Extra record is available....
                            val extraString =
                                "(" + "b " + matchScoreCard?.extras?.byes.toString() +
                                        ", lb " + matchScoreCard.extras!!.legByes + ", w " +
                                        matchScoreCard.extras!!.wides.toString() + ", nb " +
                                        matchScoreCard.extras!!.noBalls.toString() + ", p " + matchScoreCard.extras!!.penalty.toString() + ")"
                            val s = SpannableStringBuilder()
                                .bold { append(matchScoreCard?.extras?.total.toString()) }
                                .append(extraString)
                            binding?.extraRecord?.visibility = View.VISIBLE
                            binding?.extraValue?.text = s

                        } else {
                            binding?.extraRecord?.visibility = View.GONE
                        }
                    }
                }

                //for setting total
                if (matchScoreCard.scoreDetails != null) {

                    if (matchScoreCard.scoreDetails!!.overs != null) {
                        if (matchScoreCard.scoreDetails!!.overs!! > 0.0) {
                            binding?.Total?.visibility = View.VISIBLE
                            val setTotal =
                                "(" + " " + matchScoreCard.wickets?.toString() + " wkts" +
                                        "," + matchScoreCard.overs.toString() +
                                        " Ov" + ")"
                            val s = SpannableStringBuilder()
                                .bold { append(matchScoreCard?.score.toString()) }
                                .append(setTotal)
                            binding?.Total?.visibility = View.VISIBLE
                            binding?.TotalValue?.text = s
                        } else {
                            binding?.Total?.visibility = View.GONE
                        }
                    }

                }

                if (!matchScoreCard.bowler.isNullOrEmpty()) {
                    // if batting team  array is not empty...
                    val newList = matchScoreCard.bowler.filter {
                        it.overs != null
                    }
                    val listAdapter =
                        BowlerStatsAdapter(contextScore)
                    binding?.bowlingDataRecycler?.layoutManager =
                        LinearLayoutManager(contextScore)
                    binding?.bowlingDataRecycler?.adapter = listAdapter
                    listAdapter.submitList(newList)
                }

                if (!matchScoreCard.fow?.fow.isNullOrEmpty()) {

                    val stringBuilder = StringBuilder()
                    matchScoreCard.fow?.fow?.forEach { wicketsData ->
                        val string = "" + wicketsData.wktRuns.toString() + "-" +
                                wicketsData.ballNbr.toString() + "(" +
                                wicketsData.batName.toString() + ", " + wicketsData.wktOver.toString() + " Ov)" + ", "
                        stringBuilder.append(string)
                    }

                    binding?.fallOfWckValue?.text = stringBuilder.toString()
                    binding?.fallWickets?.visibility = View.VISIBLE
                    binding?.fallOfWckValue?.visibility = View.VISIBLE
                } else {
                    binding?.fallWickets?.visibility = View.GONE
                    binding?.fallOfWckValue?.visibility = View.GONE

                }

            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ScoreCardMatch>() {
        override fun areItemsTheSame(oldItem: ScoreCardMatch, newItem: ScoreCardMatch): Boolean {
            return oldItem.inningsId == newItem.inningsId
        }

        override fun areContentsTheSame(oldItem: ScoreCardMatch, newItem: ScoreCardMatch): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ScoreboardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league, context)
    }


}