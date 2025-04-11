package com.traumsportzone.live.cricket.tv.scores


import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.facebook.ads.AdSettings
import com.getkeepsafe.relinker.ReLinker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.traumsportzone.live.cricket.tv.scores.databinding.ActivityMainBinding
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.base_url_scores
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.s_token
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.socketAuth
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.socketUrl
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.LiveViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.AdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.GoogleMobileAdsConsentManager
import com.traumsportzone.live.cricket.tv.scores.streaming.adsData.NewAdManager
import com.traumsportzone.live.cricket.tv.scores.streaming.date.ScreenRotation
import com.traumsportzone.live.cricket.tv.scores.streaming.date.ScreenUtil
import com.traumsportzone.live.cricket.tv.scores.streaming.models.ApplicationConfiguration
import com.traumsportzone.live.cricket.tv.scores.streaming.models.DataModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.AppContextProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.Logger
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.AdManagerListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.DialogListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.adMoreProvider
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.appVersionCode
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.authToken
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.baseUrlChannel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.baseUrlDemo
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cementData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cementMainData
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cementMainType
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cementType
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.emptyCheck
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.filterValue
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.passVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.rateShown
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.rateUsDialogValue
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.rateUsKey
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.rateUsText
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.splash_status
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userIp
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CustomDialogue
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.SharedPreference
import com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel.OneViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DialogListener,
    NavController.OnDestinationChangedListener, AdManagerListener, ApiResponseListener {

    private lateinit var binding: ActivityMainBinding


    var context: Context? = null
    private val tAG = "MainActivityClass"

    private val viewModel by lazy {
        ViewModelProvider(this)[OneViewModel::class.java]
    }
    private val logger = Logger()
    private var navController: NavController? = null
    private var intentLink: String = ""
    private var backBoolean = false
    private var time = "0"
    private val liveViewModel by lazy {
        ViewModelProvider(this)[LiveViewModel::class.java]
    }
    private var booleanVpn: Boolean? = false

    private var adManager: AdManager? = null
    private var adStatus = false


    private var preference: SharedPreference? = null

    private var navigationTap = 0
    private var showNavigationAd = 2
    private var rateUsStatus: Boolean? = false
    private var ratingGiven: Boolean? = false
    private var dialog: Dialog? = null
    private var dialog2: Dialog? = null
    var navigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        navigation = findViewById(R.id.bottomNav)
        context = this
        setUpActionBar()
        startDestination(Constants.liveCheck)
        preference = SharedPreference(context)
        binding.lifecycleOwner = this
        binding.modelData = viewModel
        adManager = AdManager(this, this, this)
        viewModel?.apiResponseListener = this

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (backBoolean) {
                    if (ratingGiven != true) {
                        if (!isFinishing) {
                            showDialogue()
                        }
                    } else {
                        finishAffinity()
                    }
                } else {
                    binding?.navHostFragment?.findNavController()?.popBackStack()
                }
            }
        })

        binding.refreshIcon.setOnClickListener {
            liveViewModel.getsliderData()
            viewModel.onRefreshFixtures()
            viewModel.getIP()
        }
        if (Constants.liveCheck) {
            setUpNavigationGraph()
        } else {
            setUpNavigationGraph2()
        }

        setUpViewModel()
        setUpOriginal()
    }

    private fun setUpOriginal() {
        viewModel.stringValue.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                try {
                    var decryptVal = ""
                    val seperationBasedOnLetter = it?.split("_____")

                    if (seperationBasedOnLetter != null) {
                        if (seperationBasedOnLetter?.size!! > 0) {
                            decryptVal =
                                Defamation.convertDecData(seperationBasedOnLetter[seperationBasedOnLetter.size - 1])
                        }
                    }
                    viewModel.parseDataAndNotify(seperationBasedOnLetter!![0], filterValue)
//                    setUpViewModel()


                } catch (e: java.lang.Exception) {
                    viewModel.setUpError("Something is wrong with response,please retry.")
                }
            }
        })

    }



    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar1)
    }

    private fun startDestination(value: Boolean) {
        try {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)
            if (graph != null) {
                if (value == true) {
                    graph?.setStartDestination(R.id.streaming_fragment)
//
                } else {
                    graph?.setStartDestination(R.id.home)
//
                }

                val navController = navHostFragment?.navController
                navController?.setGraph(graph, intent.extras)
            }

        } catch (e: Exception) {
            Log.d("Exception", "msg")
        }

    }

    ///Navigation graph setup ......
    private fun setUpNavigationGraph() {
        binding?.bottomNav2?.visibility = View.GONE
        binding?.bottomNav?.visibility = View.VISIBLE

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController!!)
        navController!!.addOnDestinationChangedListener(this)

        binding.bottomNav.setOnItemSelectedListener { item ->
            try {
                navigationTap += 1
                if (navigationTap == showNavigationAd) {

                    if (!Constants.tapPositionProvider.equals("none", true)) {
                        if (!Constants.tapPositionProvider.equals(Constants.startApp, true)) {
                            navigationTap = 0
                            showNavigationAd += 1
                            val local = AppContextProvider.getContext()
                            local?.let {
                                NewAdManager.showAds(
                                    Constants.tapPositionProvider,
                                    this,
                                    it
                                )
                            }
                        } else {
                            if (Constants.tapPositionProvider.equals(Constants.startApp, true)) {
                                adManager?.loadAdProvider(
                                    Constants.tapPositionProvider,
                                    Constants.tap, null, null, null, null
                                )
                            }
                        }
                    } else {
                        navigationTap = 0
                    }
                    //showInterAd
                }
            } catch (e: java.lang.Exception) {
                Log.d("Exception", "msg")
            }

            if (item.itemId != navController!!.currentDestination?.id) {
                NavigationUI.onNavDestinationSelected(item, navController!!)
            }

            false
        }
    }


    private fun setUpNavigationGraph2() {
        binding?.bottomNav2?.visibility = View.VISIBLE
        binding?.bottomNav?.visibility = View.GONE

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav2.setupWithNavController(navController!!)
        navController!!.addOnDestinationChangedListener(this)

        binding.bottomNav2.setOnItemSelectedListener { item ->
            try {
                navigationTap += 1
                if (navigationTap == showNavigationAd) {

                    if (!Constants.tapPositionProvider.equals("none", true)) {
                        if (!Constants.tapPositionProvider.equals(Constants.startApp, true)) {
                            navigationTap = 0
                            showNavigationAd += 1
                            val local = AppContextProvider.getContext()
                            local?.let {
                                NewAdManager.showAds(
                                    Constants.tapPositionProvider,
                                    this,
                                    it
                                )
                            }
                        } else {
                            if (Constants.tapPositionProvider.equals(Constants.startApp, true)) {
                                adManager?.loadAdProvider(
                                    Constants.tapPositionProvider,
                                    Constants.tap, null, null, null, null
                                )
                            }
                        }
                    } else {
                        navigationTap = 0
                    }
                    //showInterAd
                }
            } catch (e: java.lang.Exception) {
                Log.d("Exception", "msg")
            }

            if (item.itemId != navController!!.currentDestination?.id) {
                NavigationUI.onNavDestinationSelected(item, navController!!)
            }

            false
        }
    }

    private fun loadTapAd() {
        //interstitial Ad loading

    }


    private fun setUpViewModel() {
        try {
            val myParcelable: DataModel? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("data", DataModel::class.java)
                } else {
                    intent.getParcelableExtra("data")
                }
            if (myParcelable != null) {
                viewModel.setUpMainData(myParcelable)
            } else {
                viewModel.setUpError("Something went wrong,please retry.")
            }
        } catch (e: java.lang.Exception) {
            viewModel.setUpError("Something went wrong,please retry.")
            Log.d("Exception", "null")
        }
        if (s_token.isNotEmpty() && base_url_scores.isNotEmpty()) {
            liveViewModel.getsliderData()
        }

        liveViewModel.isLoading.observe(this) {
            if (it) {
                binding.lottieHome.visibility = View.VISIBLE
            } else {
                binding.lottieHome.visibility = View.GONE
            }
        }



        if (userIp.contains("userIp")) {
            viewModel?.getIP()

            viewModel.isLoadingIpApi.observe(this) {
                if (it) {
                    binding.lottieHome.visibility = View.GONE
                } else {
                    binding.lottieHome.visibility = View.VISIBLE
                }
            }

        }

//        viewModel.isLoading.observe(this) {
//            if (it) {
//                binding.lottieHome.visibility = View.VISIBLE
//            } else {
//                binding.lottieHome.visibility = View.GONE
//            }
//        }

        viewModel.dataModelList.observe(this)
        {
            if (!it.app_version.isNullOrEmpty()) {
                try {
                    val version = appVersionCode
                    try {
                        val parsedInt = it.app_version!!.toInt()
                        if (parsedInt > version) {
                            if (!Constants.app_update_dialog) {
                                showAppUpdateDialog(it.app_update_text, it.is_permanent_dialog)
                                Constants.app_update_dialog = true
                            }
                        } else {
                            checkRatingDialog(it)
                        }
                    } catch (nfe: java.lang.NumberFormatException) {

                        logger.printLog(tAG, "Exception" + nfe.message)
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    logger.printLog(tAG, "Exception" + e.message)
                }
            } else {
                checkRatingDialog(it)
            }

            if (!it.application_configurations.isNullOrEmpty()) {
                showSplashMethod(it.application_configurations)
            }

            if (!it.app_ads.isNullOrEmpty()) {
                val context = AppContextProvider.getContext()
                if (!Constants.tapPositionProvider.equals("none", true)) {
                    if (context != null) {
                        if (!Constants.tapPositionProvider.equals(Constants.startApp, true)) {
                            NewAdManager.loadAdProvider(
                                Constants.tapPositionProvider, Constants.tap,
                                null, null, null, null,
                                context, this
                            )
                        }
                    }
                }

                //for more screen
                adMoreProvider =
                    adManager?.checkProvider(it.app_ads!!, Constants.adMore).toString()
                val bannerProvider =
                    adManager?.checkProvider(it.app_ads!!, Constants.adLocation1)

                if (bannerProvider != null) {
                    Constants.adLocation1Provider = bannerProvider
                }

                if (bannerProvider != null) {

                    adManager?.loadAdProvider(
                        bannerProvider, Constants.adLocation1, binding?.adView,
                        binding?.fbAdView, binding?.unityBannerView, binding?.startAppBanner
                    )
                }

            }

        }
    }

    private fun checkRatingDialog(dataModel: DataModel) {
        if (!dataModel.application_configurations.isNullOrEmpty()) {
            val status = preference?.getRateUsBool(rateUsKey)
            if (status != true) {
                dataModel.application_configurations?.forEach { config ->
                    if (config.key.equals("rateShow", true)) {
                        if (config.value != null) {
                            if (config.value.equals("True", true)) {
                                rateUsDialogValue = true
                            } else {
                                rateUsDialogValue = false
                            }
                        }
                    }

                    if (config.key.equals("rateText", true)) {
                        if (config.value != null) {
                            rateUsText = config.value.toString()
                        }
                    }
                }
                if (rateUsDialogValue) {
                    if (!rateShown) {
                        rateShown = true
                        rateUsDialog(rateUsText)
                    }
                }
            }
//                        Log.d("ValuesIn", "msf" + rateUsDialogValue + " " + rateUsText)
        }
    }

    private fun showSplashMethod(applicationConfigurations: List<ApplicationConfiguration>?) {
        var showingSplash = false
        if (!applicationConfigurations.isNullOrEmpty()) {
            applicationConfigurations.forEach { configValue ->

                if (configValue.key.equals("Time", true)) {
                    if (configValue.value != null) {
                        time = configValue.value!!
                    }
                }
                if (configValue.key.equals("baseURL", true)) {
                    if (configValue.value != null) {
                        baseUrlDemo = configValue.value.toString()
                    }
                }
                ///For setting button text
                if (configValue.key.equals("ButtonText", true)) {
                    if (configValue.value != null) {
                        binding?.splashButton?.text = configValue.value
                    }

                }

                ///For setting heading
                if (configValue.key.equals("Heading", true)) {
                    if (configValue.value != null) {
                        binding?.splashHeading?.text = configValue.value
                    }

                }

                ///For setting link
                if (configValue.key.equals("ButtonLink", true)) {
                    if (configValue.value != null) {
                        intentLink = configValue.value!!
                    }

                }

                ///For setting body
                if (configValue.key.equals("DetailText", true)) {
                    if (configValue.value != null) {
                        binding?.splashBody?.text = configValue.value
                    }
                }


                ///For setting show button
                if (configValue.key.equals("ShowButton", true)) {
                    if (configValue.value != null) {
                        if (configValue.value.equals("True", true)) {
                            binding?.splashButton?.visibility = View.VISIBLE
                        } else {
                            binding?.splashButton?.visibility = View.GONE
                        }

                    }

                }

                if (configValue.key.equals("ShowSplash", true)) {
                    if (configValue.value.equals("true", true)) {
                        if (!splash_status) {
                            showingSplash = true
                        }
                    }
                }

            }

            if (showingSplash) {
                try {
                    var timer: Int = time.toInt()
                    timer *= 1000
                    binding?.splashLayout?.visibility = View.VISIBLE
                    binding?.splashButton?.setOnClickListener {

                        val uri =
                            Uri.parse(intentLink)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)

                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        splash_status = true
                        binding?.splashLayout?.visibility = View.GONE
                    }, timer.toLong())
                } catch (e: NumberFormatException) {

                }

            }
        }

    }

    private fun rateUsDialog(
        rateText: String?
    ) {
        dialog2 = context?.let { Dialog(it) }
        dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog2?.setContentView(R.layout.rating_layout)

        val rateClicked = dialog2?.findViewById(R.id.rateUs) as Button
        val cancelCliked = dialog2?.findViewById(R.id.cancelUs) as Button
        val rateTxt = dialog2?.findViewById(R.id.rateUsTitle) as TextView

        rateTxt.text = rateText

        rateClicked.setOnClickListener {
            rateClicked()
        }

        cancelCliked.setOnClickListener {
            dialog2?.dismiss()
        }

        if (!isFinishing) {
            dialog2?.show()
        }

    }

    private fun showConsentDialog() {
//        googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(this)
//        googleMobileAdsConsentManager?.gatherConsent(this) { consentError ->
//            if (consentError != null) {
//                // Consent not obtained in current session.
//                Log.d("ConsetDialog", "${consentError.errorCode}: ${consentError.message}")
//            }
//
//
////            if (googleMobileAdsConsentManager?.canRequestAds == true) {
//////                initializeMobileAdsSdk()
////            }
////            if (googleMobileAdsConsentManager?.isPrivacyOptionsRequired == true) {
////                // Regenerate the options menu to include a privacy setting.
////                invalidateOptionsMenu()
////            }
//        }
    }

    private fun showAppUpdateDialog(appUpdateText: String?, permanent: Boolean?) {
        val dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.app_update_layout)
        val textExit = dialog?.findViewById(R.id.no_thanks) as Button
        val textRate2 = dialog.findViewById(R.id.update) as Button
        val textUpdate = dialog.findViewById(R.id.app_update_txt) as TextView

        if (permanent == true) {
            dialog.setCancelable(false)
            textExit.text = resources.getString(R.string.Exit)
        } else {
            textExit.text = resources.getString(R.string.noThanks)
            dialog.setCancelable(true)
        }

        if (appUpdateText != null) {
            textUpdate.text = appUpdateText
        }

        textExit.setOnClickListener {
            if (permanent == true) {
                Constants.app_update_dialog = false
                dialog.dismiss()
                finishAffinity()
            } else {

                dialog.dismiss()

            }
        }

        textRate2.setOnClickListener {
            rateClicked()
        }


        dialog.show()
    }

    private fun showDialogue() {
        dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.custom_layout2)

        val textExit = dialog?.findViewById(R.id.rateus) as Button
        val textRate2 = dialog?.findViewById(R.id.cancel) as Button
        textExit.setOnClickListener {
            rateClicked()
        }

        textRate2.setOnClickListener {
            Constants.app_update_dialog = false
            dialog?.dismiss()
            finishAffinity()
        }

        if (!isFinishing) {
            dialog?.show()
        }

    }

    private fun rateClicked() {
        try {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=${this.packageName}")
            )

            try {
                if (viewIntent.resolveActivity(packageManager) != null) {
                    startActivity(viewIntent)
                } else {
                    Log.d("Exceptionnnnn", "msg")
                }
            } catch (e: SecurityException) {
                Log.d("Exception", "msg")
            }

            preference?.saveRateUsBool(rateUsKey, true)

        } catch (e: ActivityNotFoundException) {
            Log.d("Exceptionnnnn", "msg" + e.message)
            preference?.saveRateUsBool(rateUsKey, true)
        }
    }


    private fun getApiBaseUrl(replaceChar: String) {

        try {
            val baseValue = Defamation.encryptBase64(replaceChar)
            val getSecretValue = Defamation.decryptPRNG(baseValue)
            Defamation.convertCementData(getSecretValue)
        } catch (e: Exception) {

            Log.d("Exception", "message" + e.message)
        }
    }

    override fun onPositiveDialogText(key: String) {
        when (key) {
            "baseValue" -> viewModel.getApiData()
            "isInternet" -> viewModel.getApiData()
            "eventValue" -> viewModel.getApiData()
        }
    }

    override fun onNegativeDialogText(key: String) {
        when (key) {
            "baseValue" -> finishAffinity()
            "isInternet" -> finishAffinity()
            "eventValue" -> finishAffinity()

        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        if (Constants.liveCheck == true) {
            if (destination.id == R.id.home || destination.id == R.id.recentFragment
                || destination.id == R.id.upcomingFragment || destination.id == R.id.browseFragment
                || destination.id == R.id.moreFragment
                || destination.id == R.id.streaming_fragment
            ) {
                binding?.bottomNav?.visibility = View.VISIBLE
            } else {
                binding?.bottomNav?.visibility = View.GONE
            }
        } else {
            if (destination.id == R.id.home || destination.id == R.id.recentFragment
                || destination.id == R.id.upcomingFragment || destination.id == R.id.browseFragment
                || destination.id == R.id.moreFragment
            ) {
                binding?.bottomNav2?.visibility = View.VISIBLE
            } else {
                binding?.bottomNav2?.visibility = View.GONE
            }
        }


        /////////
        if (Constants.liveCheck == false) {
            if (destination.id == R.id.home) {
                backBoolean = true
            } else {
                backBoolean = false
            }
        } else {
            backBoolean = destination.id == R.id.streaming_fragment
        }
        when (destination.id) {
            R.id.home -> {
//                backBoolean = true
                binding.mainFit.visibility = View.VISIBLE
            }

            R.id.streaming_fragment -> {
//                backBoolean = true
                binding.mainFit.visibility = View.VISIBLE
            }

            R.id.channel -> {
//                backBoolean = false
                binding.mainFit.visibility = View.GONE
            }

            else -> {
//                backBoolean = false
                binding.mainFit.visibility = View.GONE
//                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        adStatus = false

    }

    override fun onPause() {
        super.onPause()
        liveViewModel.stopWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        liveViewModel.stopWebSocket()

    }

    override fun onResume() {
        super.onResume()
        if (InternetUtil.isPrivateDnsSetup(this)) {
            Toast.makeText(
                this,
                "Please turn off private dns,If not found then search dns in setting search",
                Toast.LENGTH_LONG
            ).show()
            try {
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
            } catch (e: Exception) {
                Log.d("Exception", "msg")
            }
        }

        liveViewModel.isSocketUrl.observe(this) {
            if (it == true) {
                liveViewModel.runSocketCode()
            }
        }
        rateUsStatus = preference?.getRateUsBool(rateUsKey)
        if (rateUsStatus == true) {
            ratingGiven = true
            if (dialog != null) {
                dialog?.dismiss()
            }

            if (dialog2 != null) {
                dialog2?.dismiss()
            }
        } else {
            ratingGiven = false
        }
//        checkVpn()
    }

    private fun checkVpn() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    val booleanVpnCheck = hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    booleanVpn = booleanVpnCheck == true
                }
            } else {
                booleanVpn = false
            }
        }

        if (booleanVpn != null) {
            if (booleanVpn!!) {
                if (binding?.adblockLayout?.isVisible!!) {
                    /////////

                } else {
                    binding?.adblockLayout?.visibility = View.VISIBLE

                }
            } else {
                binding?.adblockLayout?.visibility = View.GONE

            }
        }

    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        if (!isFinishing) {
            try {
                CustomDialogue(this).showDialog(
                    this, "Alert", message,
                    "Retry", "Exit", "isInternet"
                )
            } catch (e: WindowManager.BadTokenException) {
                Log.d("Exception", "msg")
            }

        }
    }


}