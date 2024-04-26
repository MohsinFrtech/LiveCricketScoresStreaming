package com.traumsportzone.live.cricket.tv.scores.score.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chartboost.sdk.impl.t
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveScoresModel
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveToken
import com.traumsportzone.live.cricket.tv.scores.score.network.ApiServices
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.s_token
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.socketAuth
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons.socketUrl
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.models.DataModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.interfaces.ObserveLiveScore
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.URI
import java.net.UnknownHostException
import java.nio.ByteBuffer


class LiveViewModel : ViewModel(), ObserveLiveScore {

    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _sliderList = MutableLiveData<List<LiveScoresModel?>?>()
    val sliderList: MutableLiveData<List<LiveScoresModel?>?>
        get() = _sliderList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var isSocketUrl = MutableLiveData<Boolean>()
    private var webClient: MyWebSocketClient? = null


    init {
        //Get Data from Api for slider
        _isLoading.value=true
        isSocketUrl.value = false
    }


    fun runSocketCode() {
        try {
            val serverUri = URI(socketUrl)

            webClient = MyWebSocketClient(serverUri, liveScore = this)
            webClient?.connect()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getsliderData() {
        _isLoading.value=true

        if (socketUrl.isNotEmpty()) {
            isSocketUrl.value = true
        }

        coroutineScope.launch {

            try {
                //apiResponseListener?.onStarted()
                val addUser = LiveToken()

                addUser.token = s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getLiveDataAsync(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {

                                it.sortedByDescending { it1 ->
                                    it1.updated_at
                                }

                                _sliderList.value = it

                                apiResponseListener?.onSuccess()
                                _isLoading.value=false
                            }

                        }
                        _isLoading.value=false
                    }

                } catch (e: Exception) {

                    Log.d("live Api", "Exception : ${e.localizedMessage}")
                    Log.d("live Api", "Exception : ${e.cause}")

                    withContext(Dispatchers.Main) {

                        _isLoading.value=false

                    }

                }


            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    withContext(Dispatchers.Main) {
                        apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                        _isLoading.value=false
                    }
                } else {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value=false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value=false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                println("yell$e")
                                //  apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.value=false
                            }
                        }
                    }
                }
            }

        }

    }

    fun stopWebSocket() {
        if (webClient?.isOpen == true)
            webClient?.close(1000, "Closing connection")
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

        // Close the WebSocket connection
        if (webClient?.isOpen == true)
            webClient?.close(1000, "Closing connection")
    }


    class MyWebSocketClient(serverUri: URI?, private val liveScore: ObserveLiveScore?) :
        WebSocketClient(serverUri) {

        override fun onOpen(handshakedata: ServerHandshake?) {
            send(socketAuth)
        }

        override fun onMessage(message: String?) {
            try {
                val jobj: JSONObject? = message?.let { JSONObject(it) }
                val gson: Gson = GsonBuilder()
                    .setLenient()
                    .create()

                if (jobj?.has("type") == false) {
                    val identifier = jobj.getString("message")

                    // Use TypeToken to define the type of the list
                    val listType = object : TypeToken<List<LiveScoresModel>>() {}.type

                    // Parse the JSON array into a mutable list of LiveScoreModel objects
                    val liveScoreList: MutableList<LiveScoresModel> =
                        gson.fromJson(identifier, listType)

                    if (liveScoreList.isNotEmpty()) {
                        liveScore?.scoresData(liveScoreList)
                    }

                } else {
                    // val type = jobj?.getString("type")
                    //Log.d("SocketCode", "type : $type")
                }

            } catch (e: Exception) {
                Log.d("SocketCode2", "message exception" + e.message)

            }
        }

        override fun onMessage(bytes: ByteBuffer?) {
            super.onMessage(bytes)
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            Log.d("SocketCode", "close" + reason.toString())

        }

        override fun onError(ex: java.lang.Exception?) {
            Log.d("SocketCode", "error" + ex.toString())

        }
    }


    override fun scoresData(liveModel: List<LiveScoresModel>) {
        liveModel.sortedByDescending { it1 ->
            it1.updated_at
        }
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                _sliderList.value = null

                _sliderList.value = liveModel
            }
        }


    }

}
