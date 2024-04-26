package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemSeriesLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.SeriesScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.BrowseFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.selectedSeriesId


class SeriesItemAdapter(val context: Context, private val navigateData: NavigateData,
                        val source:String) :
    ListAdapter<SeriesScoresModel, SeriesItemAdapter.LiveSliderAdapterViewHolder>(
        LiveSliderAdapterDiffUtilCallback
    ) {

    private var binding: ItemSeriesLayoutBinding? = null

    class LiveSliderAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object LiveSliderAdapterDiffUtilCallback : DiffUtil.ItemCallback<SeriesScoresModel>() {

        override fun areItemsTheSame(
            oldItem: SeriesScoresModel,
            newItem: SeriesScoresModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SeriesScoresModel,
            newItem: SeriesScoresModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveSliderAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_series_layout, parent, false)
        binding = DataBindingUtil.bind(view)
        return LiveSliderAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: LiveSliderAdapterViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding?.model = currentList[position]
        if (currentList[position].start_date != null && currentList[position].end_date != null) {
            binding?.tvFirstTeam?.text= Cons.convertDateAndTime(currentList[position].start_date!!)

            binding?.tvSecondTeam?.text= Cons.convertDateAndTime(currentList[position].end_date!!)

        }
        binding?.executePendingBindings()


        holder.itemView.setOnClickListener {

            if (currentList[position].series_id!=null) {
                selectedSeriesId = currentList[position].series_id!!
            }

            val direction=BrowseFragmentDirections.actionSeriesFragmentToSeriesMatchFragment()
            navigateData.navigation(direction)
        }



    }


}