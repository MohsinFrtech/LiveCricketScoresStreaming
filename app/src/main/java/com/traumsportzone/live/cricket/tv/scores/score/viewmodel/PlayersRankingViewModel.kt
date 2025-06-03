package com.traumsportzone.live.cricket.tv.scores.score.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveToken
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayerDetailClass
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayersRankingModel
import com.traumsportzone.live.cricket.tv.scores.score.network.ApiServices
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PlayersRankingViewModel : ViewModel() {
    var apiResponseListener: ApiResponseListener? = null


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _teamsList = MutableLiveData<List<PlayersRankingModel>?>()
    val teamsList: MutableLiveData<List<PlayersRankingModel>?>
        get() = _teamsList

    private val _teamsOdiList = MutableLiveData<List<PlayersRankingModel>?>()
    val teamsOdiList: MutableLiveData<List<PlayersRankingModel>?>
        get() = _teamsOdiList
    private val _teamsT20List = MutableLiveData<List<PlayersRankingModel>>()
    val teamsT20List: LiveData<List<PlayersRankingModel>>?
        get() = _teamsT20List
    private val _teamsTestList = MutableLiveData<List<PlayersRankingModel>>()
    val teamsTestList: LiveData<List<PlayersRankingModel>>?
        get() = _teamsTestList

    private val _batsmenList = MutableLiveData<List<PlayersRankingModel>>()
    val batsmenList: LiveData<List<PlayersRankingModel>>?
        get() = _batsmenList

    private val _bowlersList = MutableLiveData<List<PlayersRankingModel>>()
    val bowlersList: LiveData<List<PlayersRankingModel>>?
        get() = _bowlersList

    private val _allRoundersList = MutableLiveData<List<PlayersRankingModel>>()
    val allRoundersList: LiveData<List<PlayersRankingModel>>?
        get() = _allRoundersList


    private val _playerRankingDetail = MutableLiveData<PlayerDetailClass?>()
    val playerRankingDetail: LiveData<PlayerDetailClass?>
        get() = _playerRankingDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        //_isLoading.value=true
        _teamsOdiList.value = null
    }

    fun getSpinnerData(): List<String> {
        val list = ArrayList<String>()
        list.add("ODI")
        list.add("T20")
        list.add("Test")
        return list
    }

    fun getODIRanking() {
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getOdiRankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsOdiList.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getT20Ranking() {
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()
                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getT20RankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsT20List.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTestRanking() {
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getTestRankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {

                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsTestList.value = it

//                                addAllToList()
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    ///get Player Ranking Detail.....
    fun getPlayerRankingDetail() {
        _playerRankingDetail.value = null
        try {
            _isLoading.value = true
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.id = Constants.playerId
                val body = Gson().toJson(apiToken)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService2.getPlayerInfo(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                _playerRankingDetail.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        _isLoading.value = false
                    }
                } catch (e: java.lang.Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                                _playerRankingDetail.value = null
                            }
                        }

                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                                _playerRankingDetail.value = null
                            }
                        }

                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.value = false
                                _playerRankingDetail.value = null
                            }
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d("Exception","msg")
            _isLoading.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.let {
            viewModelJob.cancel()
        }

    }
}
