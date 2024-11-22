package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemTeamNameBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.AllTeamsModel
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.TeamFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.setSafeOnClickListener

class TotalTeamsAdapter(private val listSeries: List<AllTeamsModel?>, private val teamFragment: NavigateData): ListAdapter<AllTeamsModel, TotalTeamsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ItemTeamNameBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model : AllTeamsModel){
            binding.model = model
                        binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<AllTeamsModel>(){
        override fun areItemsTheSame(oldItem: AllTeamsModel, newItem: AllTeamsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AllTeamsModel, newItem: AllTeamsModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTeamNameBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val league = currentList[position]
        holder.bind(league)

        holder.itemView.setSafeOnClickListener {


            val direction= league.team_id?.let { it1 ->
                TeamFragmentDirections.actionTeamFragmentToTeamsMatchesFragment(
                    it1,league.team_name)
            }

            direction?.let { it1 -> teamFragment.navigation(it1) }

        }
    }



}