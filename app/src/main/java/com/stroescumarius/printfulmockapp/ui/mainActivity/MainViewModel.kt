package com.stroescumarius.printfulmockapp.ui.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.stroescumarius.printfulmockapp.data.models.Resource
import com.stroescumarius.printfulmockapp.data.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: CharactersRepository) : ViewModel() {

    fun downloadCharacters() =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                val characters = repository.getCharacters()
                emit(Resource.success(data = characters))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message))
            }
        }
}