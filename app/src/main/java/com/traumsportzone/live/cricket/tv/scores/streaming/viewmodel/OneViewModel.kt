package com.traumsportzone.live.cricket.tv.scores.streaming.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.models.AddUser
import com.traumsportzone.live.cricket.tv.scores.streaming.models.DataModel
import com.traumsportzone.live.cricket.tv.scores.streaming.models.StoneFile
import com.traumsportzone.live.cricket.tv.scores.streaming.network.RetrofitController
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.appVersionCode
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.baseUrlChannel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.baseUrlDemo
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.cementType
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.channel_url_val
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.filterValue
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.myUserCheck1
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.passVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.passphraseVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.stringId
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userBaseExtraDel1
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userBaseExtraDel2
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userIp
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userLinkVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Defamation.stoneDel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class OneViewModel(application: Application?) : AndroidViewModel(application!!) {

    private val app: Application? = application
    private val tags: String = "OneViewModel"
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    val isLoading = MutableLiveData<Boolean>()
    var userLink = MutableLiveData<Boolean>()
    var userLinkStatus = MutableLiveData<Boolean>()
    var apiResponseListener: ApiResponseListener? = null
    val isLoadingIpApi = MutableLiveData<Boolean>()


    private val _dataModelList = MutableLiveData<DataModel>()
    val dataModelList: LiveData<DataModel>
        get() = _dataModelList

    private val _stringValue = MutableLiveData<String?>()
    val stringValue: LiveData<String?>
        get() = _stringValue
    init {
        userLink.value = false
        getIP()
    }

    fun parseDataAndNotify(original:String,passVal:String){

        val stringValue =
            original?.let { it1 -> stoneDel(it1, filterValue) }

        val jsonObject: JSONObject?
        try {
            jsonObject = stringValue?.let { JSONObject(it) }
            val gson: Gson = GsonBuilder().setLenient().create()

            val date =
                gson.fromJson(jsonObject.toString(), DataModel::class.java)

            _dataModelList.value = date

//            if (!date.app_version.isNullOrEmpty()) {
//
//                val version: Int = appVersionCode
//                val parsedInt = date.app_version!!.toInt()
//                if (parsedInt > version) {
//                    appUpdateAvailable.value = true
//                } else {
//                    appUpdateAvailable.value = false
//                }
//            }

            if (date.extra_1.toString().isNotEmpty()) {
                date.extra_1 = Defamation.decryptBase6(date.extra_1)
                val encrypt = date.extra_1.toString().trim()
                val yourArray: List<String> =
                    encrypt.split(userBaseExtraDel2)
                myUserCheck1 = yourArray[0].trim()
                val myRVal: List<String> =
                    yourArray[1].split(userBaseExtraDel1)
                passphraseVal = myRVal[0].trim()
            } else {

            }


        } catch (e: JSONException) {
//            isLoading.value = false
            apiResponseListener?.onFailure("Something is wrong with response")
        }
    }


    fun setUpMainData(model: DataModel) {
        _dataModelList.value = model
    }

    fun setUpError(value: String) {
        apiResponseListener?.onFailure(value)
    }

    fun onRefreshFixtures() {
        getApiData()
    }

    fun getApiData() {
        isLoading.value = true
        if (InternetUtil.isInternetOn(app)) {
            if (baseUrlChannel != "") {
                coroutineScope.launch {
                    val addUser = StoneFile()
                    addUser.id = stringId
                    addUser.auth_token = cementType
                    addUser.build_no = appVersionCode.toString()
                    //   addUser.build_no = "0"

                    val body = Gson().toJson(addUser)
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                    val getResponse = RetrofitController.apiServiceChannel.getChannelsAsync(
                        body
                    )

                    try {
                        val responseResult = getResponse.await()
                        withContext(Dispatchers.Main) {
                            responseResult.let { dataStone ->
                                _stringValue.value = dataStone.data
//                                val stringValue =
//                                    dataStone.data?.let { it1 -> stoneDel(seperationBasedOnLetter!![0], "nXU") }
//
//                                val jsonObject: JSONObject?
//                                try {
//                                    jsonObject = stringValue?.let { JSONObject(it) }
//                                    val gson: Gson = GsonBuilder().setLenient().create()
//
//                                    val date =
//                                        gson.fromJson(jsonObject.toString(), DataModel::class.java)
//
//
//                                    _dataModelList.value = date
//
//
//                                    if (!date.app_version.isNullOrEmpty()) {
//
//                                        val version: Int = appVersionCode
//                                        val parsedInt = date.app_version!!.toInt()
//                                        if (parsedInt > version) {
//                                            appUpdateAvailable.value = true
//                                        } else {
//                                            appUpdateAvailable.value = false
//                                        }
//                                    }
//
//                                    if (date.extra_1.toString().isNotEmpty()) {
//                                        date.extra_1 = Defamation.decryptBase6(date.extra_1)
//                                        val encrypt = date.extra_1.toString().trim()
//                                        val yourArray: List<String> =
//                                            encrypt.split(userBaseExtraDel2)
//                                        myUserCheck1 = yourArray[0].trim()
//                                        val myRVal: List<String> =
//                                            yourArray[1].split(userBaseExtraDel1)
//                                        passphraseVal = myRVal[0].trim()
//                                    } else {
//
//                                    }
//
//
//                                } catch (e: JSONException) {
//                                    isLoading.value = false
//                                    apiResponseListener?.onFailure("Something is wrong with response")
//                                }

                            }
                            isLoading.value = false
                        }

                    } catch (e: Exception) {
                        if (e is SocketTimeoutException || e is UnknownHostException) {
                            withContext(Dispatchers.Main) {
                                _stringValue.value = ""
                                isLoading.value = false
                                apiResponseListener?.onFailure("Server is taking too long to respond.")
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                _stringValue.value = ""
                                isLoading.value = false
                                apiResponseListener?.onFailure("Something went wrong, Please try again")
                            }
                        }
                    }
                }

            } else {
                apiResponseListener?.onFailure("Something went wrong,Please retry and restart application.")
            }

        } else {
            isLoading.value = false
            apiResponseListener?.onFailure("Internet connection lost! , please check your internet connection")
        }

    }


    fun getDemoData() {
        isLoading.value = true

        if (baseUrlDemo != "") {
            if (InternetUtil.isInternetOn(app)) {
                val addUser = AddUser()
                addUser.passphrase = passphraseVal
                addUser.channel_url = channel_url_val
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


                coroutineScope.launch {
                    val getResponse = RetrofitController.apiServiceDemo.getDemoDataAsync(
                        body
                    )

                    try {
                        val responseResult = getResponse.await()
                        withContext(Dispatchers.Main) {
                            responseResult.let {
                                if (!it?.url.isNullOrEmpty()) {

                                    userLinkVal = it?.url.toString()
                                    userLinkStatus.value = true

                                }

                            }
                            isLoading.value = false
                        }

                    } catch (e: Exception) {
                        Log.d(tags, "getDemoData Exception ..." + e.localizedMessage)

                        withContext(Dispatchers.Main) {

                            isLoading.value = false
                        }

                    }

                }

            } else {
//                isInternet.value = false
                isLoading.value = false
            }
        } else {
//            baseValue.value = false
        }
    }


    fun getIP() {
        if (InternetUtil.isInternetOn(app)) {
            isLoadingIpApi.value = false
            coroutineScope.launch {
                val getResponse = RetrofitController.apiServiceIP.getIPAsync()
                try {
                    val responseResult = getResponse?.await()
                    withContext(Dispatchers.Main) {
                        if (responseResult != null) {
                            userIp = responseResult.toString()
                            isLoadingIpApi.value = true
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        isLoadingIpApi.value = true
                        Log.d("Exception","msg")
                    }

                }
            }

        }

    }

    // On ViewModel Cleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.let {
            viewModelJob.cancel()
        }

    }

}