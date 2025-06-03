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
import com.futgenix.soccer.scores.models.FormatDataMedia3
import com.google.common.collect.ImmutableList
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.PlayerScreenDialogBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.adapters.FormatDataAdapterMedia3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.FormatSelectionMedia3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants

class VideoResolutionFragment(dialog_instan: Dialog?, exo_play: ExoPlayer) : Fragment(),
    FormatSelectionMedia3 {

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
        if (!Constants.dataFormatsMedia3.isNullOrEmpty()) {
            val adapter = FormatDataAdapterMedia3(requireActivity(), this)
            playerScreenBottomSheet?.videoRecycler?.layoutManager = LinearLayoutManager(context)
            playerScreenBottomSheet?.videoRecycler?.adapter = adapter
            adapter?.submitList(Constants.dataFormatsMedia3)
        }
    }

    @OptIn(UnstableApi::class)
    override fun navigation(viewId: FormatDataMedia3, pos: Int) {
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
                SUPPORTED_TRACK_TYPES.get(
                    0
                )
            )

            val trackSelector = player_instance_exo.trackSelector as DefaultTrackSelector

            // Assuming you have the trackGroup and trackIndex of the selected format
            val overrides = viewId.trckGroup?.mediaTrackGroup?.let {
                TrackSelectionOverride(
                    it, pos - 1
                )
            }
            overrides?.let { builder?.setOverrideForType(it) }
            trackSelectionParameters = builder?.build()
            if (trackSelectionParameters != null) {
                trackSelector.setParameters(trackSelectionParameters)
            }
        }
        dialog_video?.dismiss()
    }

}