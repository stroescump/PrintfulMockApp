package com.stroescumarius.printfulmockapp.ui.mainActivity

import android.util.Log
import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.repository.CharactersRepository
import com.stroescumarius.printfulmockapp.utils.retrofit.NetworkCallbackMethods
import retrofit2.Call

class MainActivityPresenter(private val view: MainActivityContract.View) :
    MainActivityContract.Presenter, NetworkCallbackMethods() {

    private val characterRepository by lazy { CharactersRepository(this) }

    override fun downloadCharacters() {
        view.showProgress()
        characterRepository.getCharacters(this)
    }

    override fun onCharactersReceived(characters: MutableList<Character>?) {
        if (characters != null) {
            view.updateRecycler(characters)
            view.hideProgress()
        }
    }

    override fun <T> onFailure(call: Call<T>, t: Throwable) {
        Log.d("TAG", "onFailure: ${t.message}")
        view.hideProgress()
    }
}