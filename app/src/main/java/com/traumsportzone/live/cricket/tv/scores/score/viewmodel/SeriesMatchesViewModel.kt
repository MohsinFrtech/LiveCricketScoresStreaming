package com.traumsportzone.live.cricket.tv.scores.score.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveToken
import com.traumsportzone.live.cricket.tv.scores.score.network.ApiServices
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.selectedSeriesId
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class SeriesMatchesViewModel : ViewModel() {
    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _sliderList = MutableLiveData<List<LiveScoresModel?>>()
    val sliderList: LiveData<List<LiveScoresModel?>>
        get() = _sliderList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun againLoad()
    {
        _isLoading.value=true
        loadRecentMatches()
    }
    private fun loadRecentMatches() {
        coroutineScope.launch {
            val addUser = LiveToken()

            addUser.token = Cons.s_token
            val body = Gson().toJson(addUser)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService.seriesSeriesMatches(body,selectedSeriesId)

            try {

                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {

                            it.sortedByDescending { it1 ->
                                it1.updated_at
                            }

                            _sliderList.value = it
                            _isLoading.value=false

                            /* _sliderSetChanged.call()
                             _dataSetChanged.call()*/
                            apiResponseListener?.onSuccess()
                            Log.d("RecentlyAdded","value"+_isLoading.value)
                        }

                    }

                }

            } catch (e: Exception) {

                Log.d("live Api", "Exception : ${e.localizedMessage}")
                Log.d("live Api", "Exception : ${e.cause}")

                withContext(Dispatchers.Main) {

                    _isLoading.value=false

                }

            }

        }

    }


}