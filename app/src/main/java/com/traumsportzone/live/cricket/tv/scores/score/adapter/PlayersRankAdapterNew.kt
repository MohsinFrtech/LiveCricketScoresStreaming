package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemPlayerRankBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayersRankingModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.setSafeOnClickListener

class PlayersRankAdapterNew(private val onClickListener: OnClickListener) :
    ListAdapter<PlayersRankingModel, PlayersRankAdapterNew.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemPlayerRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayersRankingModel) {
            binding.apply {
                model = item
                tvRankId.text = item.rank.toString()
                if (item.avg != null) {
                    tvPoints.text = item.avg.toString()
                } else {
                    tvPoints.text = ""
                }
                tvRatings.text = item.rating.toString()
                ivTeamLogo.setImageResource(R.drawable.placeholder)

                executePendingBindings()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<PlayersRankingModel>() {
        override fun areItemsTheSame(
            oldItem: PlayersRankingModel,
            newItem: PlayersRankingModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: PlayersRankingModel,
            newItem: PlayersRankingModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlayerRankBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setSafeOnClickListener {
            onClickListener.onClick(item)
        }
    }

    class OnClickListener(val clickListener: (item: PlayersRankingModel) -> Unit) {
        fun onClick(item: PlayersRankingModel) = clickListener(item)
    }
}
