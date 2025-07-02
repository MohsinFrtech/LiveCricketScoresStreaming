package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.CommentryLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.ComWrapper
import com.traumsportzone.live.cricket.tv.scores.score.model.CommentaryFormats


class CommentaryAdapter() :
    ListAdapter<ComWrapper, CommentaryAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: CommentryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @OptIn(UnstableApi::class)
        fun bind(wrapper: ComWrapper) {

            if (!wrapper.commentary?.commText.isNullOrEmpty()) {
                if (wrapper.commentary?.overNumber != null) {
                    if (wrapper.commentary?.overNumber != 0.0) {
                        binding?.overNumber?.text = wrapper?.commentary?.overNumber.toString()
                        binding?.overNumber?.visibility = View.VISIBLE
                    } else {
                        binding?.overNumber?.visibility = View.GONE
                    }
                } else {
                    binding?.overNumber?.visibility = View.GONE
                }

                val defaultEmptyInstance = CommentaryFormats()
                val findAllObjectsEmpty = wrapper.commentary?.commentaryFormats?.all {
                   it== defaultEmptyInstance
                }

                if (findAllObjectsEmpty == true){
                    val replaceString2 = wrapper?.commentary?.commText.toString()
                    binding?.squads?.text = replaceString2

                }else{
                    if (!wrapper.commentary?.commentaryFormats.isNullOrEmpty()) {
                        wrapper.commentary?.commentaryFormats?.forEach { format ->
                            if (format.type.equals("bold")) {
                                var s = SpannableString("")

                                var changedCommonText = wrapper.commentary?.commText
                                var count = 0

                                if (!format.value.isNullOrEmpty()) {
                                    format.value.forEach { insideValue ->

                                        if (changedCommonText?.contains(
                                                insideValue.id.toString()
                                            ) == true
                                        ) {
                                            var replaceString = ""
                                            var len2 = insideValue.value?.length
//                                        var len =
//                                            commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                                count
//                                            )?.length


                                            val indexOf =
                                                changedCommonText?.indexOf(insideValue.id.toString())

                                            val length = len2?.let { indexOf?.plus(it) }
                                            replaceString = changedCommonText!!.replace(
                                                insideValue.id.toString(),
                                                insideValue.value.toString(),
//                                            commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                                count
//                                            ).toString(),
                                                true
                                            )

                                            setBoldValue(replaceString)
                                            var ss = SpannableString(replaceString)
                                            val boldSpan = StyleSpan(Typeface.BOLD)
                                            if (indexOf != null) {
                                                if (length != null) {
                                                    ss.setSpan(
                                                        boldSpan,
                                                        indexOf,
                                                        length,
                                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                                    )
                                                }
                                            }
                                            changedCommonText = ss.toString()
                                            s = ss
                                            count++

                                        } else {

                                        }

                                    }
                                    binding?.squads?.text = s
                                }
                            }
                            else if (format?.type?.equals("italic") == true){
                                var s = SpannableString("")

                                var changedCommonText = wrapper.commentary?.commText
                                var count = 0

                                if (!format.value.isNullOrEmpty()) {
                                    format.value.forEach { insideValue ->

                                        if (changedCommonText?.contains(
                                                insideValue.id.toString()
                                            ) == true
                                        ) {
                                            var replaceString = ""
                                            var len2 = insideValue.value?.length
//                                        var len =
//                                            commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                                count
//                                            )?.length


                                            val indexOf =
                                                changedCommonText?.indexOf(insideValue.id.toString())

                                            val length = len2?.let { indexOf?.plus(it) }
                                            replaceString = changedCommonText!!.replace(
                                                insideValue.id.toString(),
                                                insideValue.value.toString(),
//                                            commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                                count
//                                            ).toString(),
                                                true
                                            )

                                            setBoldValue(replaceString)
                                            var ss = SpannableString(replaceString)
                                            val boldSpan = StyleSpan(Typeface.BOLD)
                                            if (indexOf != null) {
                                                if (length != null) {
                                                    ss.setSpan(
                                                        boldSpan,
                                                        indexOf,
                                                        length,
                                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                                    )
                                                }
                                            }
                                            changedCommonText = ss.toString()
                                            s = ss
                                            count++

                                        } else {

                                        }

                                    }
                                    binding?.squads?.text = s
                                }
                            }

                        }
//                }else if (!commentary.commentaryFormats?.italic?.formatId.isNullOrEmpty()) {
//                    var s = SpannableString("")
//
//                    var changedCommonText = commentary.commText
//                    var count = 0
//                    commentary.commentaryFormats?.italic?.formatId?.forEach { formatIdNext ->
//
//                        if (changedCommonText?.contains(
//                                formatIdNext.toString()
//                            ) == true
//                        ) {
//                            var replaceString = ""
//
//                            var len =
//                                commentary.commentaryFormats!!.italic?.formatValue?.get(count)?.length
//                            val indexOf = changedCommonText?.indexOf(formatIdNext)
//                            val length = len?.let { indexOf?.plus(it) }
//                            replaceString = changedCommonText!!.replace(
//                                formatIdNext.toString(),
//                                commentary.commentaryFormats!!.italic?.formatValue?.get(count)
//                                    .toString(),
//                                true
//                            )
//
//                            setBoldValue(replaceString)
//                            var ss = SpannableString(replaceString)
//                            val boldSpan = StyleSpan(Typeface.BOLD)
//                            if (indexOf != null) {
//                                if (length != null) {
//                                    ss.setSpan(
//                                        boldSpan,
//                                        indexOf,
//                                        length,
//                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//                                    )
//                                }
//                            }
//                            changedCommonText = ss.toString()
//                            s = ss
//                            count++
//
//                        } else {
////                                s.append(commentary.commText.toString())
////                           replaceString = commentary.commText.toString()
////                           binding?.squads?.text = replaceString
//                        }
//                    }
////                        s.bold {
////                            append(
////                                commentary.commentaryFormats!!.bold?.formatValue?.get(
////                                    0
////                                ).toString()
////                            )
////                        }.append(changedCommonText)
//
//
//                    binding?.squads?.text = s
                    }
                }
                //////
            }

            if (wrapper?.commentary?.overSeparator != null) {
                if (wrapper?.commentary?.overSeparator!!.runs != null) {
                    if (wrapper?.commentary?.overSeparator!!.runs!! >= 0.0) {
                        binding?.mainBatsmanHead?.visibility = View.VISIBLE
                        binding?.fulloverNumber?.text =
                            wrapper?.commentary?.overSeparator?.overNum?.toString()
                        binding?.tvSeries?.text =
                            wrapper?.commentary?.overSeparator!!.oSummary?.toString()

                        binding?.tvMatch?.text =
                            wrapper?.commentary?.overSeparator!!.batStrikerNames?.get(0)
                                .toString() + "  " + wrapper?.commentary?.overSeparator!!.batStrikerRuns.toString() + "(" + wrapper?.commentary?.overSeparator!!.batStrikerBalls + ")"

                        binding?.tvDateTime?.text =
                            wrapper?.commentary?.overSeparator!!.batNonStrikerNames?.get(0)
                                .toString() + "  " + wrapper?.commentary?.overSeparator!!.batNonStrikerRuns.toString() + "(" + wrapper?.commentary?.overSeparator!!.batNonStrikerBalls + ")"

                        binding?.tvVenue?.text =
                            wrapper?.commentary?.overSeparator!!.bowlNames?.get(0)
                                .toString() + "  " + wrapper?.commentary?.overSeparator!!.bowlOvers.toString() + "-" + wrapper?.commentary?.overSeparator!!.bowlWickets + "-" + wrapper?.commentary?.overSeparator!!.bowlRuns.toString() + "-" + wrapper?.commentary?.overSeparator!!.bowlMaidens.toString()

                    }
                } else {
                    binding?.mainBatsmanHead?.visibility = View.GONE
                }
            } else {
                binding?.mainBatsmanHead?.visibility = View.GONE
            }

            binding.executePendingBindings()
        }

        private fun setBoldValue(replaceString: String) {

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ComWrapper>() {
        override fun areItemsTheSame(oldItem: ComWrapper, newItem: ComWrapper): Boolean {
            return oldItem.commentary?.inningsId == newItem?.commentary?.inningsId
        }

        override fun areContentsTheSame(oldItem: ComWrapper, newItem: ComWrapper): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(CommentryLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        val league = currentList[position]
        holder.bind(league)


    }


}