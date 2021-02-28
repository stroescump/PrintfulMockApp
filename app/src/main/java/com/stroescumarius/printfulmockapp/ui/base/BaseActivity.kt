package com.stroescumarius.printfulmockapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stroescumarius.printfulmockapp.utils.networkmonitor.NetworkMonitor

abstract class BaseActivity : AppCompatActivity() {
    private val networkMonitor by lazy {
        NetworkMonitor(
            this
        ) { onNetworkAvailable() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkMonitor.checkAvailableNetworks()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.startNetworkMonitoring()
    }

    override fun onPause() {
        super.onPause()
        networkMonitor.stopNetworkMonitoring()
    }

    abstract fun isDataCached(): Boolean
    abstract fun onNetworkAvailable()
}