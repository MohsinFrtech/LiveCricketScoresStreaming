package com.traumsportzone.live.cricket.tv.scores.score.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.traumsportzone.live.cricket.tv.scores.score.model.LiveToken
import com.traumsportzone.live.cricket.tv.scores.score.model.MainHighestAverage
import com.traumsportzone.live.cricket.tv.scores.score.model.MainHighestScore
import com.traumsportzone.live.cricket.tv.scores.score.model.MainHighestStrrikeRate
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostFifties
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostHundereds
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostNinties
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostRuns
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostSix
import com.traumsportzone.live.cricket.tv.scores.score.model.MainMostWicket
import com.traumsportzone.live.cricket.tv.scores.score.model.MianMostFour
import com.traumsportzone.live.cricket.tv.scores.score.network.ApiServices
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.ODImatchTypeId
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.T20matchTypeId
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.TestmatchTypeId
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestAvgstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestScorestattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestSrstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFiftiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFoursstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostHundredsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostNinetiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostRunsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostSixesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostWicketstattypeid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class StatsViewModel: ViewModel() {

    private var apiResponseListener: ApiResponseListener? = null
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    //region Most Wickets
    private val _mostWickets = MutableLiveData<MainMostWicket>()
    val mostWicket : LiveData<MainMostWicket> get() = _mostWickets

    private val _mostt20Wickets = MutableLiveData<MainMostWicket>()
    val mostt20Wicket : LiveData<MainMostWicket> get() = _mostt20Wickets

    private val _mostTestWickets = MutableLiveData<MainMostWicket>()
    val mostTestWicket : LiveData<MainMostWicket> get() = _mostTestWickets
    //endregion

    //region Most Runs
    private val _mostODIRuns = MutableLiveData<MainMostRuns>()
    val mostODIRuns : LiveData<MainMostRuns> get() = _mostODIRuns

    private val _mostT20Runs = MutableLiveData<MainMostRuns>()
    val mostT20Runs : LiveData<MainMostRuns> get() = _mostT20Runs

    private val _mostTestRuns = MutableLiveData<MainMostRuns>()
    val mostTestRuns : LiveData<MainMostRuns> get() = _mostTestRuns
    //endregion

    //region Highest Score
    private val _mostODIHighestScore = MutableLiveData<MainHighestScore>()
    val mostODIHighestScore : LiveData<MainHighestScore> get() = _mostODIHighestScore

    private val _mostT20HighestScore = MutableLiveData<MainHighestScore>()
    val mostT20HighestScore : LiveData<MainHighestScore> get() = _mostT20HighestScore

    private val _mostTestHighestScore = MutableLiveData<MainHighestScore>()
    val mostTestHighestScore : LiveData<MainHighestScore> get() = _mostTestHighestScore
    //endregion

    //region Highest Sr
    private val _mostODIHighestSr = MutableLiveData<MainHighestStrrikeRate>()
    val mostODIHighestSr : LiveData<MainHighestStrrikeRate> get() = _mostODIHighestSr

    private val _mostT20HighestSr = MutableLiveData<MainHighestStrrikeRate>()
    val mostT20HighestSr : LiveData<MainHighestStrrikeRate> get() = _mostT20HighestSr

    private val _mostTestHighestSr = MutableLiveData<MainHighestStrrikeRate>()
    val mostTestHighestSr : LiveData<MainHighestStrrikeRate> get() = _mostTestHighestSr
    //endregion

    //region Most Hundered
    private val _mostODIMostHundered = MutableLiveData<MainMostHundereds>()
    val mostODIMostHundered : LiveData<MainMostHundereds> get() = _mostODIMostHundered

    private val _mostT20MostHundered = MutableLiveData<MainMostHundereds>()
    val mostT20MostHundered : LiveData<MainMostHundereds> get() = _mostT20MostHundered

    private val _mostTestMostHundered = MutableLiveData<MainMostHundereds>()
    val mostTestMostHundered : LiveData<MainMostHundereds> get() = _mostTestMostHundered
    //endregion

    //region Most Fifties
    private val _mostODIMostFifties = MutableLiveData<MainMostFifties>()
    val mostODIMostFifties : LiveData<MainMostFifties> get() = _mostODIMostFifties

    private val _mostT20MostFifties = MutableLiveData<MainMostFifties>()
    val mostT20MostFifties : LiveData<MainMostFifties> get() = _mostT20MostFifties

    private val _mostTestMostFifties = MutableLiveData<MainMostFifties>()
    val mostTestMostFifties : LiveData<MainMostFifties> get() = _mostTestMostFifties
    //endregion

    //region Most Sixes
    private val _mostODIMostSix = MutableLiveData<MainMostSix>()
    val mostODIMostSix : LiveData<MainMostSix> get() = _mostODIMostSix

    private val _mostT20MostSix = MutableLiveData<MainMostSix>()
    val mostT20MostSix : LiveData<MainMostSix> get() = _mostT20MostSix

    private val _mostTestMostSix = MutableLiveData<MainMostSix>()
    val mostTestMostSix : LiveData<MainMostSix> get() = _mostTestMostSix
    //endregion

    //region Most Four
    private val _mostODIMostFour = MutableLiveData<MianMostFour>()
    val mostODIMostFour : LiveData<MianMostFour> get() = _mostODIMostFour

    private val _mostT20MostFour = MutableLiveData<MianMostFour>()
    val mostT20MostFour : LiveData<MianMostFour> get() = _mostT20MostFour

    private val _mostTestMostFour = MutableLiveData<MianMostFour>()
    val mostTestMostFour : LiveData<MianMostFour> get() = _mostTestMostFour
    //endregion

    //region Highest Avg
    private val _mostODIHighestAverage = MutableLiveData<MainHighestAverage>()
    val mostODIHighestAverage : LiveData<MainHighestAverage> get() = _mostODIHighestAverage

    private val _mostT20HighestAverage = MutableLiveData<MainHighestAverage>()
    val mostT20HighestAverage : LiveData<MainHighestAverage> get() = _mostT20HighestAverage

    private val _mostTestHighestAverage = MutableLiveData<MainHighestAverage>()
    val mostTestHighestAverage : LiveData<MainHighestAverage> get() = _mostTestHighestAverage
    //endregion

    //region Highest Avg
    private val _mostODIMostNinties = MutableLiveData<MainMostNinties>()
    val mostODIMostNinties : LiveData<MainMostNinties> get() = _mostODIMostNinties

    private val _mostT20MostNinties = MutableLiveData<MainMostNinties>()
    val mostT20MostNinties : LiveData<MainMostNinties> get() = _mostT20MostNinties

    private val _mostTestMostNinties = MutableLiveData<MainMostNinties>()
    val mostTestMostNinties : LiveData<MainMostNinties> get() = _mostTestMostNinties
    //endregion

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _isLoadingT20 = MutableLiveData<Boolean>()
    val isLoadingT20: LiveData<Boolean> get() = _isLoadingT20

    init {
        _isLoading.value = true
        _isLoadingT20.value = true
    }


    //region Most Wickets
    fun loadMostWickets(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostWicketstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostWickets(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostWickets.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: java.lang.Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:java.lang.Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadMostT20Wickets(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostWicketstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostWickets(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostt20Wickets.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadMostTestWickets(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostWicketstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostWickets(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestWickets.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Most Runs
    fun loadMostODIRuns(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostRunsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostRuns(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIRuns.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadMostT20Runs(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostRunsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostRuns(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){
                                _mostT20Runs.value = it
                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }

    fun loadMostTestRuns(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostRunsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostRuns(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestRuns.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Highest Score
    fun loadODIHighestScore(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = highestScorestattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestScore(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIHighestScore.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadT20HighestScore(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = highestScorestattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestScore(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20HighestScore.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }

    fun loadTestHighestScore(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = highestScorestattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestScore(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestHighestScore.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Highest Strike rate
    fun loadODIHighestSr(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = highestSrstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestStrike(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIHighestSr.value = it
//                                _isLoadingT20.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            _isLoadingT20.value = false
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadT20HighestSr(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = highestSrstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestStrike(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){
                                _mostT20HighestSr.value = it
                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }

    fun loadTestHighestSr(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = highestSrstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestStrike(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestHighestSr.value = it
//                                _isLoadingT20.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            _isLoadingT20.value = false
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Most Hundereds
    fun loadODIMostHundered(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostHundredsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostHundered(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIMostHundered.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadT20MostHundered(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostHundredsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostHundered(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20MostHundered.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }

    fun loadTestMostHundered(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostHundredsstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostHundered(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestMostHundered.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    //endregion

    //region Most Fifties
    fun loadODIMostFifties(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostFiftiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFifties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIMostFifties.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    fun loadT20MostFifties(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostFiftiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFifties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20MostFifties.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }
    fun loadTestMostFifties(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostFiftiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFifties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestMostFifties.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    //endregion

    //region Most Sixes
    fun loadODIMostSixes(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostSixesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostSix(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIMostSix.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    fun loadT20MostSixes(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostSixesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostSix(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20MostSix.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }

    fun loadTestMostSixes(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostSixesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostSix(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestMostSix.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Most Fours
    fun loadODIMostFours(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostFoursstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFour(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIMostFour.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    fun loadT20MostFours(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostFoursstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFour(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20MostFour.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }
    fun loadTestMostFours(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostFoursstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostFour(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestMostFour.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }
    //endregion

    //region Highest Avg
    fun loadODIHighestAvg(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = highestAvgstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestAvg(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIHighestAverage.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadT20HighestAvg(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = highestAvgstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestAvg(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostT20HighestAverage.value = it

                                apiResponseListener?.onSuccess()
                                _isLoadingT20.value = false
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }
    fun loadTestHighestAvg(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = highestAvgstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getHighestAvg(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestHighestAverage.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    //endregion

    //region Most Ninties
    fun loadODIMostNinties(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = ODImatchTypeId
                apiToken.stat_type_id = mostNinetiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostNinties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostODIMostNinties.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }

    fun loadT20MostNinties(){
        _isLoadingT20.value = true
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = T20matchTypeId
                apiToken.stat_type_id = mostNinetiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostNinties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){
                                _mostT20MostNinties.value = it
                            }
                        }
                        apiResponseListener?.onSuccess()
                        _isLoadingT20.value = false
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoadingT20.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
            _isLoadingT20.value = false
        }
    }
    fun loadTestMostNinties(){
        try {
            coroutineScope.launch {
                val apiToken = LiveToken()
                apiToken.token = Cons.s_token
                apiToken.match_type_id = TestmatchTypeId
                apiToken.stat_type_id = mostNinetiesstattypeid

                val body = Gson().toJson(apiToken).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val getResponse = ApiServices.retrofitService2.getMostNinties(body)

                try {
                    val responseResult = getResponse.await()
                    withContext(Dispatchers.Main){
                        responseResult.let {
                            if (it!=null){

                                _mostTestMostNinties.value = it
                                _isLoading.value = false
                                apiResponseListener?.onSuccess()
                            }
                        }
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                    Log.d("ResponseIssue", e.message.toString())
                }
            }
        }catch (e:Exception){
            Log.d("ResponseNewIssue", e.message.toString())
        }
    }


    //endregion

}