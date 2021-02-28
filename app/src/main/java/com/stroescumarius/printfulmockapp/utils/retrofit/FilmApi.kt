package com.stroescumarius.printfulmockapp.utils.retrofit

import com.stroescumarius.printfulmockapp.data.models.Film
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmApi {
    @GET("films/{id}/")
    suspend fun getFilm(@Path("id") filmId: String): Film
}