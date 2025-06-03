package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cricgenix.live.cricket.appmodels.VenuModel
import com.traumsportzone.live.cricket.tv.scores.databinding.VenuNameItemsBinding
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.venufragments.VenuFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class VenuAdapter(
    private val listSeries: List<VenuModel?>,
    private val appNavigation: NavigateData
) : ListAdapter<VenuModel, VenuAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: VenuNameItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: VenuModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<VenuModel>() {
        override fun areItemsTheSame(oldItem: VenuModel, newItem: VenuModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VenuModel, newItem: VenuModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(VenuNameItemsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        val league = currentList[position]

        holder.bind(league)

        holder.itemView.setOnClickListener {
            if (league.venue_id != null) {
                Constants.selectedVenu = league.venue_id!!
            }

            val directionFromVenuToMatches =
                VenuFragmentDirections.actionVenueFragmentToVenuMatchesFragment()
            appNavigation.navigation(directionFromVenuToMatches)
        }
    }


}