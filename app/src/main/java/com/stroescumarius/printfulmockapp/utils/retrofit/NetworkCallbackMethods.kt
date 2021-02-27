package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.models.Film

abstract class NetworkCallbackMethods : CallbackCharacter, CallbackFilm {

    override fun onFilmReceived(film: Film) {
    }

    override fun onCharacterReceived(character: Character?) {
    }

    override fun onCharactersReceived(characters: MutableList<Character>?) {
    }

    abstract fun <T> onFailure(call: retrofit2.Call<T>, t: Throwable)
}