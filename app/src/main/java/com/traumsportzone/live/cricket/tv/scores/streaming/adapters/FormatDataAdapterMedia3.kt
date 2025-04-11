package com.traumsportzone.live.cricket.tv.scores.streaming.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.futgenix.soccer.scores.models.FormatDataMedia3
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FormatItemBinding

import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.FormatSelectionMedia3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.positionClick2
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.previousClick2


class FormatDataAdapterMedia3(
    val context: Context,
    private val formatSelection: FormatSelectionMedia3
) : ListAdapter<FormatDataMedia3, FormatDataAdapterMedia3.EventAdapterViewHolder>(
    EventAdapterDiffUtilCallback
) {


    private var bindingFormat: FormatItemBinding? = null
    private var liveChannelCount = 0

    object EventAdapterDiffUtilCallback : DiffUtil.ItemCallback<FormatDataMedia3>() {
        override fun areItemsTheSame(oldItem: FormatDataMedia3, newItem: FormatDataMedia3): Boolean {
            return oldItem.token == newItem.token
        }

        override fun areContentsTheSame(oldItem: FormatDataMedia3, newItem: FormatDataMedia3): Boolean {
            return oldItem == newItem
        }

    }


    class EventAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgChannel = itemView.findViewById<ImageView>(R.id.check_uncheck)
        val textChannel = itemView.findViewById<TextView>(R.id.formatValue)
        val mainBack = itemView.findViewById<ConstraintLayout>(R.id.mainClick)

    }


    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {

        if (currentList[position].token==null)
        {
            if (position==0) {
                holder.textChannel.text = "Auto"
            }
        }
        else {
            holder.textChannel?.text = currentList[position].token?.height.toString() + " p"

        }
        if (position == positionClick2) {
            holder.imgChannel.visibility=View.VISIBLE
        } else {
            holder.imgChannel.visibility=View.GONE
        }
        holder.mainBack.setOnClickListener {
            formatSelection.navigation(currentList[position],position)
            positionClick2 = holder.absoluteAdapterPosition
            if (previousClick2 == -1)
                previousClick2 = positionClick2
            else {
                notifyItemChanged(previousClick2)
                previousClick2 = positionClick2
            }
            notifyItemChanged(positionClick2)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.format_item, parent, false)
        bindingFormat = DataBindingUtil.bind(view)
        return EventAdapterViewHolder(view)
    }
}