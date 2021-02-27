package com.stroescumarius.printfulmockapp.models

data class CharactersRetrofitMapping(
    var count: Int,
    var next: String,
    var previous: String,
    var results: MutableList<Character>
) {
}