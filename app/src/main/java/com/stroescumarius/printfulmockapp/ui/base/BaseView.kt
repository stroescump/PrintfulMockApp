package com.stroescumarius.printfulmockapp.ui.base

import android.view.View

interface BaseView {
    fun getRootView(): View
    fun initPresenter()
    fun showProgress()
    fun hideProgress()
    fun displayNoInternetMessage()
}