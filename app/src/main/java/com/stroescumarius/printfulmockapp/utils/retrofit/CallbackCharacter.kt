package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.data.models.Character

interface CallbackCharacter {
    fun onCharacterReceived(character: Character?)
    fun onCharactersReceived(characters: MutableList<Character>?)
}