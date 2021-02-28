package com.stroescumarius.printfulmockapp.data.models

import kotlin.properties.Delegates

object Variables {
    var isNetworkConnected: Boolean by Delegates.observable(false) { _, _, _ -> }

    var isFirstTimeNetworkCheck: Boolean by Delegates.observable(false) { _, _, _ -> }
}