package com.stroescumarius.printfulmockapp.ui.characterDetails

import com.stroescumarius.printfulmockapp.models.Film
import com.stroescumarius.printfulmockapp.repository.FilmsRepository
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkCallbackMethods
import retrofit2.Call

class CharacterDetailsPresenter(private val view: CharacterDetails) :
    CharacterDetailsContract.Presenter,
    NetworkCallbackMethods() {
    private val filmsRepository by lazy { FilmsRepository(this) }

    override fun downloadFilm(filmId: String){
        filmsRepository.getFilm(filmId)
    }

    override fun onFilmReceived(film: Film) {
        view.showProgress()
        view.updateFilmList(film)
        view.hideProgress()
    }

    override fun <T> onFailure(call: Call<T>, t: Throwable) {
        view.hideProgress()
    }

}