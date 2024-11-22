package com.traumsportzone.live.cricket.tv.scores.tv.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.ViewModelProvider
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.date.ProcessingFile
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Category
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Channel
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Event
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.adAfter
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.adBefore
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.locationAfter
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType1
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType2
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userType3
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation.improveDeprecatedCode
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import com.traumsportzone.live.cricket.tv.scores.tv.activities.TvPlayScreen
import com.traumsportzone.live.cricket.tv.scores.tv.presenters.ChannelPresenter

class TvChannelFragment : BrowseSupportFragment(), AdManagerListener {

    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mMetrics: DisplayMetrics
    private var channelUrl: String? = null
    private var channelType: String? = null
    private var channelList: MutableList<Channel> = ArrayList()
    private var adManager: AdManager? = null
    private var adProviderName = "none"
    private var adStatus = false


    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[OneViewModel::class.java]
    }

    private var localVal: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareBackgroundManager()
        setupUIElements()

    }

    override fun onResume() {
        super.onResume()
        gettingNavArgument()
    }


    ///Getting nav arguments....
    private fun gettingNavArgument() {
        channelList.clear()

        val destinationName = activity?.intent?.getStringExtra("destination")
        val clickName = activity?.intent?.getStringExtra("clickName")
        if (destinationName != null) {

            viewModel.dataModelList.observe(viewLifecycleOwner) {
                if (it != null) {
                    localVal = it.extra_3
                    if (destinationName.equals(TvHomeFragment.channelCategory[0], true)) {
                        if (!it.events.isNullOrEmpty()) {

                            var channelData: Event? = null
                            for (event in it.events!!) {
                                if (event.name.equals(clickName, true)) {
                                    channelData = event
                                }
                            }

                            if (channelData != null) {
                                if (!channelData.channels.isNullOrEmpty()) {


                                    for (channel in channelData.channels!!) {
                                        if (channel.live!!) {
                                            channelList.add(channel)
                                        }
                                    }

                                    if (channelList.isNotEmpty()) {

                                        ////Sorting list of channels.....
                                        channelList.sortBy { it1 ->
                                            it1.priority
                                        }

                                        settingUpChannels(channelList)
                                    }

                                }
                            }
                        }

                    } else {
                        if (!it.categories.isNullOrEmpty()) {

                            var channelData: Category? = null
                            for (category in it.categories!!) {
                                if (category.name.equals(clickName, true)) {
                                    channelData = category
                                }
                            }

                            if (channelData != null) {
                                if (!channelData.channels.isNullOrEmpty()) {


                                    for (channel in channelData.channels!!) {
                                        if (channel.live!!) {
                                            channelList.add(channel)
                                        }
                                    }

                                    if (channelList.isNotEmpty()) {

                                        ////Sorting list of channels.....
                                        channelList.sortBy { it1 ->
                                            it1.priority
                                        }

                                        settingUpChannels(channelList)
                                    }
                                }
                            }
                        }
                    }

                    if (!it.app_ads.isNullOrEmpty()) {
                        adProviderName =
                            adManager?.checkProvider(it.app_ads!!, adBefore).toString()
                        locationAfter =
                            adManager?.checkProvider(it.app_ads!!, adAfter).toString()
                        adManager?.loadAdProvider(
                            adProviderName, adBefore,
                            null,
                            null,
                            null,null
                        )
                    }
                }
            }
        }
    }


    private fun settingUpChannels(channelList: MutableList<Channel>) {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        val listRowAdapter = ArrayObjectAdapter(ChannelPresenter(requireContext()))
        for (j in 0 until channelList.size) {
            listRowAdapter.add(channelList[j])
        }

        val header = HeaderItem(0, "Channels")
        rowsAdapter.add(ListRow(header, listRowAdapter))
        adapter = rowsAdapter

        onItemViewClickedListener = getDefaultItemViewClickedListener()
        onItemViewSelectedListener = getDefaultItemSelectedListener()
    }

    private fun getDefaultItemViewClickedListener(): OnItemViewClickedListener {
        return OnItemViewClickedListener { _, _, _, _ ->

            if (adStatus) {
                if (!adProviderName.equals("none", true)) {
                    adManager?.showAds(adProviderName)
                } else {
                    intentUser()
                }
            } else {
                intentUser()
            }
        }
    }

    private fun intentUser() {
        if (channelType.equals(
                userType2,
                true
            )
        ) {
            val intent = Intent(context, TvPlayScreen::class.java)
            intent.putExtra("link_append", "linkAppend")
            intent.putExtra("channel_type", userType2)
            startActivity(intent)


        } else {
            if (localVal?.isNotEmpty() == true) {

                val processingFile = ProcessingFile()
                Constants.defaultString = processingFile.getChannelType(localVal)

                val token= channelUrl?.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                val linkAppend = channelUrl+ token
                // select channel for p2p
                val userType = if (channelType.equals(
                        userType1, true
                    )
                ) {
                    userType1
                } else {
                    userType3
                }

                val intent = Intent(context, TvPlayScreen::class.java)
                intent.putExtra("link_append", linkAppend)
                intent.putExtra("channel_type", userType)
                startActivity(intent)
            }
        }
    }

    private fun getDefaultItemSelectedListener(): OnItemViewSelectedListener {
        return OnItemViewSelectedListener { _, item, _, _ ->
            if (item is Channel) {
                channelUrl = item.url

                channelType = item.channel_type

            }
        }
    }


    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity?.window)
        mMetrics = DisplayMetrics()
        mBackgroundManager.color = Color.parseColor("#191721")
    }


    private fun setupUIElements() {
        val colorText = (""
                + "<font color=\"#FFFFFF\"><bold>"
                + requireContext().getString(R.string.app_name)
                + "</bold></font>")
        title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(colorText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            requireContext().packageName
        }
        // over title
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        // set search icon color
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        intentUser()
    }

}