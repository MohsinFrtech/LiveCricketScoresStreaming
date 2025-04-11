package com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.traumsportzone.live.cricket.tv.scores.BuildConfig
import com.traumsportzone.live.cricket.tv.scores.R

object DebugChecker {

    fun checkDebugging(context: Context?): Boolean {
        if (!BuildConfig.DEBUG) {
            if (Settings.Secure.getInt(
                    context?.contentResolver,
                    Settings.Global.ADB_ENABLED,
                    0
                ) == 1
            ) {
                if (isContextValid(context))
                {
                    showDialog(context)
                }

                return true
            }
        }

        return false
    }

    fun isContextValid(context: Context?): Boolean {
        if (context is Activity) {
            if (context.isFinishing || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.isDestroyed)) {
                return false
            }
        }
        return true
    }

    private fun showDialog(context: Context?) {
        context?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error!")
                .setCancelable(false)
                .setMessage(context.getString(R.string.usb_debugging))
                .setPositiveButton(
                    "Goto Disable"
                ) { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                        val componentName = intent.resolveActivity(context.packageManager)
                        if (componentName == null) {
                            Toast.makeText(
                                context, "No Activity to handle Intent action",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            context.startActivity(intent)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
//                .setNegativeButton(
//                    "Exit"
//                ) { _, _ -> //"exit"
//                }
                .show()
        }

    }

}