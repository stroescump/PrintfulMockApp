package com.stroescumarius.printfulmockapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.stroescumarius.printfulmockapp.data.models.Resource
import com.stroescumarius.printfulmockapp.data.models.Variables
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

    fun hasAvailableNetwork(): Boolean {
        return Variables.isNetworkConnected
    }

    fun handleError(resource: Resource<Any>, view: View) {
        resource.message?.let { message -> displayErrorMessage(message, view) }
    }

    private fun displayErrorMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    abstract fun isDataCached(): Boolean
    abstract fun onNetworkAvailable()
    abstract fun showProgress()
    abstract fun hideProgress()
    abstract fun displayNoInternetMessage(view: View)
}