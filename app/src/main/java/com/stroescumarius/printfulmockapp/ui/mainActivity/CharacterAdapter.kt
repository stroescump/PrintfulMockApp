package com.stroescumarius.printfulmockapp.ui.mainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stroescumarius.printfulmockapp.databinding.ItemCharacterRecyclerviewBinding
import com.stroescumarius.printfulmockapp.data.models.Character
import com.stroescumarius.printfulmockapp.data.models.MeasureUnit
import com.stroescumarius.printfulmockapp.data.constants.Constants
import java.util.*
import kotlin.collections.ArrayList

class CharacterAdapter(private val onItemClickCallback: (character: Character, position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val characters = ArrayList<Character>()
    private lateinit var binding: ItemCharacterRecyclerviewBinding

    fun setData(characters: MutableList<Character>) {
        this.characters.addAll(characters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemCharacterRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return CharacterVH(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentCharacter = characters[position]
        holder as CharacterVH
        holder.bind(currentCharacter, position)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterVH(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(character: Character, position: Int) {
            setAvatar(position)
            setName(character)
            setHeight(character)
            setMass(character)
            setupListener(character, position)
        }

        private fun setupListener(character: Character, position: Int) {
            binding.root.setOnClickListener { onItemClickCallback(character, position) }
        }

        private fun setAvatar(position: Int) {
            val currentAvatar = Constants.AVATARS[position]
            Glide.with(view).load(currentAvatar).into(binding.ivCharacterAvatar)
        }

        private fun setName(character: Character) {
            binding.tvCharacterName.text = character.name
        }

        private fun setHeight(character: Character) {
            val measureUnit = MeasureUnit.CM.convertToString()
            binding.tvCharacterHeight.text = character.height.toString()
                .appendMeasureUnit(measureUnit)
        }

        private fun setMass(character: Character) {
            val measureUnit = MeasureUnit.KG.convertToString()
            binding.tvCharacterMass.text = character.mass.toString()
                .appendMeasureUnit(measureUnit)
        }

    }

}

fun String.appendMeasureUnit(measureUnit: String): String {
    return "$this $measureUnit"
}

fun MeasureUnit.convertToString(): String {
    return this.toString().toLowerCase(Locale.getDefault())
}

