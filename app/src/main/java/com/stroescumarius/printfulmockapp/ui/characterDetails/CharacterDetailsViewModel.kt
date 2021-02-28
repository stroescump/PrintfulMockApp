package com.stroescumarius.printfulmockapp.ui.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.stroescumarius.printfulmockapp.data.models.Resource
import com.stroescumarius.printfulmockapp.data.repository.FilmRepository
import kotlinx.coroutines.Dispatchers

class CharacterDetailsViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun downloadFilm(filmId: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val film = filmRepository.getFilm(filmId)
                emit(Resource.success(film))
            } catch (ex: Exception) {
                emit(Resource.error(ex.message, null))
            }
        }
}
