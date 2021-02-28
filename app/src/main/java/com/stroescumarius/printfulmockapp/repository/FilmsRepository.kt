package com.stroescumarius.printfulmockapp.repository

import android.util.Log
import com.stroescumarius.printfulmockapp.models.Film
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkCallbackMethods
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkServiceBuilder
import com.stroescumarius.printfulmockapp.utils.retrofit.SwapiEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsRepository(private val callback: NetworkCallbackMethods) {
    private val request by lazy { NetworkServiceBuilder.buildService(SwapiEndpoints::class.java) }

    fun getFilm(filmId: String) {
        Log.d("Retrofit", "getFilm: ${request.toString()}")
        request.getFilm(filmId).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                val film = response.body()
                if (film != null) {
                    callback.onFilmReceived(film)
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                callback.onFailure(call, t)
                call.clone().enqueue(this)
            }
        })
    }
}