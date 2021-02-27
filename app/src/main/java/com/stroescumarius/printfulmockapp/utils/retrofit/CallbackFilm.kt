package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.models.Film

interface CallbackFilm {
    fun onFilmReceived(film: Film)
}