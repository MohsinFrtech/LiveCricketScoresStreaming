package com.traumsportzone.live.cricket.tv.scores.streaming.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.MainFragmentDirections
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemLayoutEventsBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Event
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.NavigateData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.setSafeOnClickListener

class EventAdapter(
    val context: Context, private val navigateData: NavigateData, val list: List<Event?>,
    private val adType: String, private val adManager: AdManager
) : ListAdapter<Event, EventAdapter.EventAdapterViewHolder>(EventAdapterDiffUtilCallback) {


    private var bindingEvent: ItemLayoutEventsBinding? = null
    private var liveChannelCount = 0

    object EventAdapterDiffUtilCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }


    class EventAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageEvent)
        val textView = itemView.findViewById<TextView>(R.id.eventName)
    }


    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
//        bindingEvent?.data = currentList[position]
        holder.textView.text = currentList[position].name
        loadImage(holder.imageView, currentList[position].image_url)
        if (!currentList[position].channels.isNullOrEmpty()) {
            liveChannelCount = 0
            for (i in currentList[position].channels!!) {
                if (i.live == true) {
                    liveChannelCount++
                }
            }
        }

        holder.itemView.setSafeOnClickListener {
            val direction = MainFragmentDirections.actionHomeToChannel(currentList[position])
            navigateData.navigation(direction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_events, parent, false)
        bindingEvent = DataBindingUtil.bind(view)
        return EventAdapterViewHolder(view)
    }
}