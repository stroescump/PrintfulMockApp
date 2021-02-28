package com.stroescumarius.printfulmockapp.data.models

data class CharactersBulk(
    var count: Int,
    var next: String,
    var previous: String,
    var results: MutableList<Character>
) {
}