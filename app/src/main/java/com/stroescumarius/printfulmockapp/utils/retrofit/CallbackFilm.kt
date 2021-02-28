package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.data.models.Film

interface CallbackFilm {
    fun onFilmReceived(film: Film)
}