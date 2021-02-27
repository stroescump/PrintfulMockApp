package com.stroescumarius.printfulmockapp.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {
    private val connectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfNetworkAvailable()
    }

    private fun checkIfNetworkAvailable() {
        TODO("Not yet implemented")
    }
    
    override fun onResume() {
        super.onResume()

    }
}