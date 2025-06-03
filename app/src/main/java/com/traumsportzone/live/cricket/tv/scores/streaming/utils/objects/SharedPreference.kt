package com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects

import android.content.Context
import android.content.SharedPreferences
import com.traumsportzone.live.cricket.tv.scores.R

class SharedPreference(context: Context?) {

    private var mPref: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    init {
        mPref = context?.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        mEditor = mPref?.edit()
    }

    fun saveString(key: String, value: String) {
        mEditor?.putString(key, value)
        mEditor?.commit()
    }

    fun getString(key: String):String? {
        return mPref?.getString(key, "")
    }

    fun saveBool(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun getBool(key: String):Boolean? {
       return mPref?.getBoolean(key, false)
    }

    fun saveRateUsBool(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun getRateUsBool(key: String):Boolean? {
        return mPref?.getBoolean(key, false)
    }

    fun saveConsent(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun getConsentStatus(key: String):Boolean? {
        return mPref?.getBoolean(key, false)
    }

    fun saveNotificationPermission(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun getNotificationPermission(key: String):Boolean? {
        return mPref?.getBoolean(key, false)
    }

    fun getAppOpeningValue(key: String):Int? {
        return mPref?.getInt(key,0)
    }


    fun saveAppOpeningValue(key: String, value: Int) {
        mEditor?.putInt(key, value)
        mEditor?.commit()
    }
}