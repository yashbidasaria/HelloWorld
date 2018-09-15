package com.github.yasushi.hansel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.LocalBroadcastManager

class NetworkConnectionReceiver(context: Context?) : BroadcastReceiver() {
    companion object {
        private val TAG: String = "NetworkConnectionReceiver"
        @JvmStatic val NOTIFY_NETWORK_CHANGE: String = "NOTIFY_NETWORK_CONNECTION"
        @JvmStatic val EXTRA_IS_CONNECTED: String = "EXTRA_IS_CONNECTED"
        @JvmStatic val EXTRA_IS_WIFI: String = "EXTRA_IS_WIFI"
    }

    constructor(): this(null)

    private var context: Context? = context

    override fun onReceive(c: Context?, i: Intent?) {
        this.context = c
        val localIntent = Intent(NOTIFY_NETWORK_CHANGE)
        localIntent.putExtra(EXTRA_IS_CONNECTED, isOnline())
        localIntent.putExtra(EXTRA_IS_WIFI, isWifi())
        LocalBroadcastManager.getInstance(c!!).sendBroadcast(localIntent)
    }

    private fun getNetworkInfo() : NetworkInfo? {
       val connManager = this.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connManager.activeNetworkInfo
    }

    private fun isOnline() : Boolean {
        val connMgr = this.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo = connMgr.activeNetworkInfo ?: return false
        return info.isConnected
    }

    private fun isWifi() : Boolean {
        val connMgr = this.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo = connMgr.activeNetworkInfo?: return false

        return info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

}

