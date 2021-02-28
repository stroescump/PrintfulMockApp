package com.stroescumarius.printfulmockapp.ui.characterDetails

import com.stroescumarius.printfulmockapp.models.Film
import com.stroescumarius.printfulmockapp.ui.base.BaseView

interface CharacterDetailsContract {
    interface View : BaseView {
        fun updateFilmList(film: Film)
        fun displayNoFilmsMessage()
        fun hasMovies(): Boolean
        fun hasErrorNoMovie(): Boolean
        fun clearFilms()
    }

    interface Presenter {
        fun downloadFilm(filmId: String)
    }
}