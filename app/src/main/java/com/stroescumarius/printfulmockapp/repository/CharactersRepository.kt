package com.stroescumarius.printfulmockapp.repository

import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.models.CharactersRetrofitMapping
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkCallbackMethods
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkServiceBuilder
import com.stroescumarius.printfulmockapp.utils.retrofit.SwapiEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersRepository(private val callback: NetworkCallbackMethods) {
    private val request by lazy { NetworkServiceBuilder.buildService(SwapiEndpoints::class.java) }

    fun getCharacter(characterId: String) {
        request.getCharacter(characterId).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                val character = response.body()
                callback.onCharacterReceived(character)
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun getCharacters(callback: NetworkCallbackMethods) {
        request.getCharacters().enqueue(object : Callback<CharactersRetrofitMapping> {
            override fun onResponse(
                call: Call<CharactersRetrofitMapping>,
                response: Response<CharactersRetrofitMapping>
            ) {
                val characters = response.body()
                callback.onCharactersReceived(characters?.results)
            }

            override fun onFailure(call: Call<CharactersRetrofitMapping>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }
}