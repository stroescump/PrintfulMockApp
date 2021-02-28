package com.stroescumarius.printfulmockapp.data.repository

import com.stroescumarius.printfulmockapp.data.models.Character
import com.stroescumarius.printfulmockapp.utils.retrofit.CharacterApi

interface CharactersRepository {
    suspend fun getOneCharacter(characterId: String): Character
    suspend fun getCharacters(): MutableList<Character>
}

class CharactersRepositoryImpl(
    private val characterApi: CharacterApi
) :
    CharactersRepository {

    override suspend fun getOneCharacter(characterId: String): Character {
        return characterApi.getOneCharacter(characterId)
    }

    override suspend fun getCharacters(): MutableList<Character> {
        return characterApi.getCharacters().results
    }
}