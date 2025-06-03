package com.traumsportzone.live.cricket.tv.scores.streaming.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.recyclerview.widget.LinearLayoutManager
import com.futgenix.soccer.scores.adapters.FormatDataAdapterAudioMedia3
import com.futgenix.soccer.scores.models.FormatDataAudioMedia3
import com.google.common.collect.ImmutableList
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.PlayerScreenDialogBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.FormatSelectionAudioMedia3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class LanguageResolutionFragment(dialog_instan: Dialog?, exo_play: ExoPlayer) :Fragment() ,
    FormatSelectionAudioMedia3 {
    private var playerScreenBottomSheet: PlayerScreenDialogBinding? = null
    val dialog_video = dialog_instan
    val player_instance_exo = exo_play

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.player_screen_dialog, container, false)
        playerScreenBottomSheet = DataBindingUtil.bind(layout)
        playerScreenBottomSheet?.lifecycleOwner = this

        setFormatDataAdapter()
        return layout
    }

    private fun setFormatDataAdapter() {
        if (!Constants.dataFormatsAudioMedia3.isNullOrEmpty()) {
            val adapter = FormatDataAdapterAudioMedia3(requireActivity(), this)
            playerScreenBottomSheet?.videoRecycler?.layoutManager = LinearLayoutManager(context)
            playerScreenBottomSheet?.videoRecycler?.adapter = adapter
            adapter?.submitList(Constants.dataFormatsAudioMedia3)
        }
    }

    @OptIn(UnstableApi::class)
    override fun navigation(viewId: FormatDataAudioMedia3, pos: Int, countryCode: String?) {
        val SUPPORTED_TRACK_TYPES = ImmutableList.of(
            C.TRACK_TYPE_VIDEO, C.TRACK_TYPE_AUDIO, C.TRACK_TYPE_TEXT, C.TRACK_TYPE_IMAGE
        )
        if (viewId.token == null) {
            val trackSelector = player_instance_exo.trackSelector as DefaultTrackSelector
            val newParameters = TrackSelectionParameters.Builder(/* context */).build()
            trackSelector.setParameters(newParameters)
        } else {

            var trackSelectionParameters: TrackSelectionParameters? = null
            ////
            val builder: TrackSelectionParameters.Builder? =
                player_instance_exo?.trackSelectionParameters?.buildUpon()
            builder?.clearOverridesOfType(
                C.TRACK_TYPE_AUDIO
            )
            // Getting track format and language.....
//            val format = viewId.trckGroup?.getTrackFormat(pos)
//            val trackLanguage = format?.language


            val trackSelector = player_instance_exo.trackSelector as DefaultTrackSelector
            // Assuming you have the trackGroup and trackIndex of the selected format

            var overrides: TrackSelectionOverride? = null
            ////////////////////////
            for (i in 0 until viewId.trckGroup?.length!!) {

                val trackFormat = viewId.trckGroup?.mediaTrackGroup!!.getFormat(i)
                if (trackFormat.language == countryCode) {
                    // Change to your target language
                    overrides = viewId.trckGroup?.mediaTrackGroup?.let {
                        TrackSelectionOverride(
                            it, i
                        )
                    }
//                    Log.d("msgss","m"+trackFormat.language+" "+i)
//                    addOverride(TrackSelectionOverrides.TrackOverride(group.mediaTrackGroup!!, i))
                }
            }

            ////////////


//            val trackSelectionOverride =
//                viewId.trckGroup?.mediaTrackGroup?.let { TrackSelectionOverride(it, 0) }

            overrides?.let { builder?.setOverrideForType(it) }
            trackSelectionParameters = builder?.build()
            if (trackSelectionParameters != null) {
                trackSelector.setParameters(trackSelectionParameters)
            }
        }
        dialog_video?.dismiss()
    }
}