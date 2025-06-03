package com.traumsportzone.live.cricket.tv.scores.score.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricgenix.live.cricket.appmodels.VenuModel
import com.google.gson.Gson
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveToken
import com.traumsportzone.live.cricket.tv.scores.score.network.ApiServices
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import java.lang.Exception

class VenuViewModel : ViewModel() {
    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _venuList = MutableLiveData<List<VenuModel?>>()
    val venuList: LiveData<List<VenuModel?>>
        get() = _venuList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _venuMatchesList = MutableLiveData<List<LiveScoresModel?>>()
    val venuMatchesList: LiveData<List<LiveScoresModel?>>
        get() = _venuMatchesList

    fun againLoad() {
        _isLoading.value = true
        loadAllVenusFromRemote()
    }

    private fun loadAllVenusFromRemote() {
        coroutineScope.launch {
            val apiToken = LiveToken()
            apiToken.token = Cons.s_token
            val body = Gson().toJson(apiToken)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService2.getAllVenues(body)

            try {
                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {
                            it.sortedByDescending { it1 ->
                                it1.id
                            }

                            _venuList.value = it
                            _isLoading.value = false
                            apiResponseListener?.onSuccess()
                        }

                    }

                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }

        }

    }

    fun loadVenueMatchesFromRemote() {
        _isLoading.value = true
        coroutineScope.launch {
            val apiToken = LiveToken()
            apiToken.token = Cons.s_token
            val body = Gson().toJson(apiToken)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService2.getAllVenuesMatches(body,Constants.selectedVenu)

            try {
                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {
                            it.sortedByDescending { it1 ->
                                it1.id
                            }

                            _venuMatchesList.value = it
                            _isLoading.value = false
                            apiResponseListener?.onSuccess()
                        }

                    }

                }

            } catch (e: java.lang.Exception) {

                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }


}