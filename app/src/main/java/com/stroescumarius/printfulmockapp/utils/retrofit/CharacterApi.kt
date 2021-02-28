package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.data.models.Character
import com.stroescumarius.printfulmockapp.data.models.CharactersBulk
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("people/{characterId}/")
    suspend fun getOneCharacter(@Path("characterId") characterId: String): Character

    @GET("people/")
    suspend fun getCharacters(): CharactersBulk
}