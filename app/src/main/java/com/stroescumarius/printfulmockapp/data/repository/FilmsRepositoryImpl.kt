package com.stroescumarius.printfulmockapp.data.repository

import com.stroescumarius.printfulmockapp.data.models.Film
import com.stroescumarius.printfulmockapp.utils.retrofit.FilmApi

interface FilmRepository {
    suspend fun getFilm(filmId: String): Film
}

class FilmsRepositoryImpl(private val filmApi: FilmApi) : FilmRepository {

    override suspend fun getFilm(filmId: String): Film {
        return filmApi.getFilm(filmId)
    }
}