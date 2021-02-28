package com.stroescumarius.printfulmockapp.ui.characterDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.stroescumarius.printfulmockapp.R
import com.stroescumarius.printfulmockapp.data.models.Resource
import com.stroescumarius.printfulmockapp.data.models.Variables
import com.stroescumarius.printfulmockapp.data.repository.FilmRepository
import com.stroescumarius.printfulmockapp.ui.base.MainApplication
import kotlinx.coroutines.Dispatchers

class CharacterDetailsViewModel(
    private val filmRepository: FilmRepository,
    application: Application
) : AndroidViewModel(application) {

    fun downloadFilm(filmId: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            if (hasActiveNetwork())
                try {
                    val film = filmRepository.getFilm(filmId)
                    emit(Resource.success(film))
                } catch (ex: Exception) {
                    emit(Resource.error(ex.message, null))
                }
            else emit(Resource.error(getNoNetworkError(), null))
        }

    private fun hasActiveNetwork(): Boolean {
        return Variables.isNetworkConnected
    }

    private fun getNoNetworkError() =
        getApplication<MainApplication>().applicationContext.resources.getString(
            R.string.error_no_internet
        )
}
