package com.stroescumarius.printfulmockapp.utils.networkmonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import com.stroescumarius.printfulmockapp.models.Variables


class NetworkMonitor
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(
    private val activity: AppCompatActivity,
    private val onNetworkAvailable: () -> Unit
) {
    private val connectivityManager by lazy { activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    fun startNetworkMonitoring() {
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Variables.isNetworkConnected = true
                onNetworkAvailable()
            }

            override fun onLost(network: Network) {
                Variables.isNetworkConnected = false
            }
        }
        connectivityManager.registerNetworkCallback(
            request,
            networkCallback
        )
    }

    fun stopNetworkMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun checkAvailableNetworks() {
        val networkInfo = connectivityManager.activeNetwork
        if (networkInfo != null) {
            Variables.isNetworkConnected = true
            Variables.isFirstTimeNetworkCheck = true
        }
    }

}