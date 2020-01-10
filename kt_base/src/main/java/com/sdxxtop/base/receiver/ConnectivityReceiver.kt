package com.sdxxtop.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.sdxxtop.base.receiver.CommonNetworkUtils

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-26 09:26
 * Version: 1.0
 * Description:
 *
 * 这个去监听网络变化
 */
class ConnectivityReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "ConnectivityReceiver"
        fun register(context: Context) {
            val intentFilter = IntentFilter()
            val connectivityReceiver = ConnectivityReceiver()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(connectivityReceiver, intentFilter)

            Log.i(TAG, "register  ")
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "onReceive  ${intent?.action}")
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val type = connectivityManager.activeNetworkInfo?.type
            when (type) {
                ConnectivityManager.TYPE_MOBILE -> {
                    Toast.makeText(context, "移动数据网络", Toast.LENGTH_LONG).show()
                }
                ConnectivityManager.TYPE_WIFI -> {
                    Toast.makeText(context, "WIFI网络", Toast.LENGTH_LONG).show()
                }
                else -> {
                    if (!CommonNetworkUtils.isNetworkAvailable(context)) {
                        Toast.makeText(context, "当前网络不稳定", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}