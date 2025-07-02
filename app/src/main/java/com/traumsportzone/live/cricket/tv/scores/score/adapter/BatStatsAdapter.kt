package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.R.attr.text
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ScoreCardItemBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.Batsman


class BatStatsAdapter(val context: Context) :
    ListAdapter<Batsman, BatStatsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ScoreCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(batData: Batsman) {
            if (batData.balls!=null)
            {
                if (batData.balls!! >0){
                    binding?.battingTeamScores?.visibility=View.VISIBLE
                    binding?.teamTxt2?.text = batData.name.toString()
                    binding?.team12?.text = batData.runs?.toString()
                    binding?.team22?.text = batData.balls?.toString()
                    binding?.team32?.text = batData?.fours?.toString()
                    binding?.team42?.text = batData?.sixes?.toString()
                    binding?.teamTotal2?.text = batData?.strkRate?.toString()

                    if (!batData.outDec.isNullOrEmpty()){
                        binding?.outStatusValue?.text = batData.outDec.toString()
                        binding?.outLay?.visibility=View.VISIBLE
                    }
                    else
                    {
                        binding?.outLay?.visibility=View.GONE
                    }
                }
                else
                {
                    binding?.battingTeamScores?.visibility=View.GONE
                }
            }
            else
            {
                binding?.battingTeamScores?.visibility=View.GONE
            }


        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Batsman>() {
        override fun areItemsTheSame(oldItem: Batsman, newItem: Batsman): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Batsman, newItem: Batsman): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ScoreCardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val batData = currentList[position]
        holder.bind(batData)
    }


}