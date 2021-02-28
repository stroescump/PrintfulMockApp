package com.stroescumarius.printfulmockapp.ui.characterDetails

import com.stroescumarius.printfulmockapp.models.Film
import com.stroescumarius.printfulmockapp.models.Variables
import com.stroescumarius.printfulmockapp.repository.FilmsRepository
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkCallbackMethods
import retrofit2.Call

class CharacterDetailsPresenter(private val view: CharacterDetails) :
    CharacterDetailsContract.Presenter,
    NetworkCallbackMethods() {
    private val filmsRepository by lazy { FilmsRepository(this) }

    override fun downloadFilm(filmId: String) {
        if (isNetworkAvailable()) {
            view.showProgress()
            filmsRepository.getFilm(filmId)
        } else {
            view.displayNoInternetMessage()
            view.displayNoFilmsMessage()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return Variables.isNetworkConnected
    }

    override fun onFilmReceived(film: Film) {
        if (view.hasErrorNoMovie())
            view.clearFilms()
        view.updateFilmList(film)
        view.hideProgress()
    }

    override fun <T> onFailure(call: Call<T>, t: Throwable) {
        view.hideProgress()
    }

}