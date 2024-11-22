package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.MoreLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.SharedPreference
import com.traumsportzone.live.cricket.tv.scores.utils.Logger


class MoreFragment : Fragment(), AdManagerListener {

    private val logger = Logger();
    private val tags = "MoreFragment"
    private var binding: MoreLayoutBinding? = null

    private var preference: SharedPreference? = null

    private var adManager: AdManager? = null
    private var adStatus = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.more_layout, container, false)

        binding = DataBindingUtil.bind(view)
        preference = SharedPreference(requireContext())

        adManager = AdManager(requireContext(), requireActivity(), this)

        if (Constants.adMoreProvider.equals(Constants.startApp,true))
        {
            adManager?.loadAdProvider(
                Constants.adMoreProvider, Constants.adMore,
                null, null, null, null
            )
        }
        val getStatus = preference?.getBool(Constants.preferenceKey)
        if (getStatus == true) {
            @Suppress("DEPRECATION")
            binding?.notificationOnOff?.thumbDrawable?.setColorFilter(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.green
                ), PorterDuff.Mode.MULTIPLY
            )
            @Suppress("DEPRECATION")
            binding?.notificationOnOff?.trackDrawable?.setColorFilter(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.white
                ), PorterDuff.Mode.MULTIPLY
            )
            binding?.notificationOnOff?.isChecked = true
        } else {
            @Suppress("DEPRECATION")
            binding?.notificationOnOff?.thumbDrawable?.setColorFilter(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorRed
                ), PorterDuff.Mode.MULTIPLY
            )
            @Suppress("DEPRECATION")
            binding?.notificationOnOff?.trackDrawable?.setColorFilter(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.grey
                ), PorterDuff.Mode.SRC_IN
            )
            binding?.notificationOnOff?.isChecked = false
        }

        //Rateus Click
        binding?.rateUsLay?.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireContext().packageName)
                    )
                )
            } catch (e: ActivityNotFoundException) {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + requireContext().packageName)
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    logger.printLog(tags, "exception : ${e.localizedMessage}")
                }
            }
        }
        ////Shareus layout....
        binding?.ShareUs?.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT, "Please download this app for live  streaming.\n" +
                            "https://play.google.com/store/apps/details?id=" + requireContext().packageName
                )
                intent.type = "text/plain"
                startActivity(intent)
            } catch (e: Exception) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }

        ////More Aps layout....
        binding?.MainMoreApps?.setOnClickListener {
            try {

                if (!Constants.adMoreProvider.equals("none", true)) {
                    adManager?.showAds(Constants.adMoreProvider)
                }

            } catch (e: Exception) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }


        binding?.notificationOnOff?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                @Suppress("DEPRECATION")
                binding?.notificationOnOff?.thumbDrawable?.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.green
                    ), PorterDuff.Mode.MULTIPLY
                )
                @Suppress("DEPRECATION")
                binding?.notificationOnOff?.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    ), PorterDuff.Mode.MULTIPLY
                )
                preference?.saveBool(Constants.preferenceKey, true)
                //FirebaseMessaging.getInstance().subscribeToTopic("event")
            } else {
                @Suppress("DEPRECATION")
                binding?.notificationOnOff?.thumbDrawable?.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorRed
                    ), PorterDuff.Mode.MULTIPLY
                )
                @Suppress("DEPRECATION")
                binding?.notificationOnOff?.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.grey
                    ), PorterDuff.Mode.SRC_IN
                )
                preference?.saveBool(Constants.preferenceKey, false)

                ///Subscribe to particular event.....
               // FirebaseMessaging.getInstance().unsubscribeFromTopic("event")

            }

        }

        binding?.mainFeedback?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, Array(1) { "contact@traumsportzone.com" })
            intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
            startActivity(Intent.createChooser(intent, "Send Email..."))
        }

        ///Privacy policy layout...
        binding?.MainPrivacyPolicy?.setOnClickListener {
            try {
                val url = "https://traumsportzone.com"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (Constants.adMoreProvider != "none") {
            loadMoreAd()
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)
    }

    override fun onAdFinish() {
        adStatus = false

        if (Constants.adMoreProvider != "none") {
            loadMoreAd()
        }

    }

    private fun loadMoreAd() {
        Log.d("moreProviderAd","ad"+ Constants.adMoreProvider)
        //interstitial Ad loading
        if (!Constants.adMoreProvider.equals(Constants.startApp,true))
        {
            adManager?.loadAdProvider(
                Constants.adMoreProvider, Constants.adMore,
                null, null, null, null
            )
        }

    }
}