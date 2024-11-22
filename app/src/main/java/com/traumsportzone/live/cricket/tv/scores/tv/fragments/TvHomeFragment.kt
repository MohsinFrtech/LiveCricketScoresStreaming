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
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Category
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Event
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import com.traumsportzone.live.cricket.tv.scores.tv.activities.TvChannelActivity
import com.traumsportzone.live.cricket.tv.scores.tv.presenters.EventPresenter

class TvHomeFragment : BrowseSupportFragment() ,AdManagerListener{

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[OneViewModel::class.java]
    }

    private var mBackgroundUri: String? = null
    private var destination: String = ""
    private var clickName: String = ""
    private var adProviderMiddleName = "none"
    private var adManager: AdManager? = null
    private var adStatus = false

    companion object {
        val channelCategory = arrayOf(
            "Events",
            "Categories"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareBackgroundManager()
        setupUIElements()
    }

    override fun onResume() {
        super.onResume()
        loadRows()
    }

    private fun loadRows() {
        adManager = AdManager(requireContext(), requireActivity(), this)

        viewModel.dataModelList.observe(viewLifecycleOwner) {
            ////Checking if event list is not empty...
            if (!it.events.isNullOrEmpty()) {
                val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
                val liveEvents: MutableList<Event> =
                    ArrayList()
                val liveCategories: MutableList<Category> =
                    ArrayList()
                if (!it.app_ads.isNullOrEmpty()) {
                    //showAds
                    adProviderMiddleName =
                        adManager?.checkProvider(it.app_ads!!, Constants.adMiddle).toString()

                    adManager?.loadAdProvider(
                        adProviderMiddleName, Constants.adMiddle,
                        null, null, null, null
                    )
                }

                ////Iterating through list of events....
                for (i in it.events!!) {
                    if (i.live!! && !i.channels.isNullOrEmpty()) {
                        liveEvents.add(i)
                    }
                }
                if (liveEvents.isNotEmpty()) {
                    liveEvents.sortBy { it1 ->
                        it1.priority
                    }
                }

                for (i in 0 until 2) {
                    if (i == 0) {

                        if (liveEvents.isNotEmpty()) {
                            val listRowAdapter =
                                ArrayObjectAdapter(EventPresenter(requireContext()))
                            for (j in 0 until liveEvents.size) {
                                listRowAdapter.add(liveEvents[j])
                            }
                            val header = HeaderItem(i.toLong(), channelCategory[i])
                            rowsAdapter.add(ListRow(header, listRowAdapter))
                        }

                    } else {


                    }

                }
                adapter = rowsAdapter

                onItemViewClickedListener = getDefaultItemViewClickedListener()
                onItemViewSelectedListener = getDefaultItemSelectedListener()
            }
        }
    }

    private fun prepareBackgroundManager() {
        val mBackgroundManager = BackgroundManager.getInstance(requireActivity())
        mBackgroundManager?.attach(requireActivity().window)
        DisplayMetrics()
        mBackgroundManager.color =
            ContextCompat.getColor(requireContext(), R.color.colorPrimary)

    }

    private fun setupUIElements() {
        val colorText = (""
                + "<font color=\"#FFFFFFF\"><bold>"
                + requireContext().getString(R.string.app_name)
                + "</bold></font>")
        title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(colorText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            requireContext().packageName
        }

        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        // set fastLane (or headers) background color
        brandColor =  Color.parseColor("#191721")
        // set search icon color
    }

    private fun getDefaultItemViewClickedListener(): OnItemViewClickedListener {
        return OnItemViewClickedListener { _, _, _, _ ->

            if (adStatus) {
                if (!adProviderMiddleName.equals("none", true)) {
                    adManager?.showAds(adProviderMiddleName)
                } else {
                    moveToChannelScreen()
                }
            } else {
                moveToChannelScreen()
            }

        }
    }

    private fun moveToChannelScreen() {
        val intent = Intent(requireContext(), TvChannelActivity::class.java)
        intent.putExtra("destination", destination)
        intent.putExtra("clickName", clickName)
        startActivity(intent)

    }

    private fun getDefaultItemSelectedListener(): OnItemViewSelectedListener {
        return OnItemViewSelectedListener { _, item, _, _ ->
            if (item is Event) {
                destination = channelCategory[0]
                clickName = item.name.toString()
                mBackgroundUri = item.image_url
            }

            if (item is Category) {
                destination = channelCategory[1]
                clickName = item.name.toString()
                mBackgroundUri = item.image_url

            }
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {

    }

}

