package com.stroescumarius.printfulmockapp.ui.mainActivity

import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.ui.base.BaseView

interface MainActivityContract {
    interface View : BaseView{
        fun showShadowDecoration()
        fun updateRecycler(characters : MutableList<Character>)
    }

    interface Presenter{
        fun downloadCharacters()
    }
}