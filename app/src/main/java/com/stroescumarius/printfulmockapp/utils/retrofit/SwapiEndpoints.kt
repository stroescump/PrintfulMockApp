package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.models.CharactersRetrofitMapping
import com.stroescumarius.printfulmockapp.models.Film
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiEndpoints {
    @GET("people/{characterId}/")
    fun getCharacter(@Path("characterId") characterId: String): Call<Character>

    @GET("people/")
    fun getCharacters(): Call<CharactersRetrofitMapping>

    @GET("films/{filmId}/")
    fun getFilm(@Path("filmId") filmId: String): Call<Film>
}