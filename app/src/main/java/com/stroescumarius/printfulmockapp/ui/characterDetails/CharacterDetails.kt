package com.stroescumarius.printfulmockapp.ui.characterDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.stroescumarius.printfulmockapp.R
import com.stroescumarius.printfulmockapp.databinding.ActivityCharacterDetailsBinding
import com.stroescumarius.printfulmockapp.databinding.ItemCharacterDetailsBinding
import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.models.Film
import com.stroescumarius.printfulmockapp.ui.base.BaseActivity
import com.stroescumarius.printfulmockapp.utils.constants.Constants

class CharacterDetails : BaseActivity(), CharacterDetailsContract.View {
    private var presenter: CharacterDetailsPresenter? = null
    private lateinit var binding: ActivityCharacterDetailsBinding
    private lateinit var itemBinding: ItemCharacterDetailsBinding
    private var character: Character? = null
    private var characterPhotoID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        initPresenter()
        checkForAvailableCharacter()
    }

    override fun initPresenter() {
        presenter = CharacterDetailsPresenter(this)
    }

    private fun setupViews() {
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        val scrollableLayout = getRootDetailsLayout()
        itemBinding = ItemCharacterDetailsBinding.bind(scrollableLayout)
        setContentView(binding.root)
    }

    private fun getRootDetailsLayout() = binding.scrollViewDetailsHolder[0]

    private fun checkForAvailableCharacter() {
        character = retrieveCharacter()
        characterPhotoID = retrieveCharacterPhotoID()
        if (character != null && characterPhotoID != null) {
            showProgress()
            populateMovieList()
            populateOtherFields()
            hideProgress()
        }
    }

    private fun retrieveCharacterPhotoID(): Int =
        intent.getIntExtra(Constants.CHARACTER_PHOTO_ID, 0)

    private fun retrieveCharacter(): Character? =
        intent.getParcelableExtra(Constants.CHARACTER)

    private fun populateMovieList() {
        for (film in character!!.films!!) {
            val filmId = getFilmID(film)
            presenter?.downloadFilm(filmId)
        }
    }

    private fun getFilmID(film: String) = film.filter { (it.isDigit()) }

    private fun populateOtherFields() {
        setAvatar()
        setName()
        setHeight()
        setMass()
        setHairColor()
        setSkinColor()
        setEyeColor()
        setBirthYear()
        setGender()
    }

    private fun setGender() {
        itemBinding.tvCharacterDetailsGender.text =
            resources.getString(R.string.gender, character!!.gender)
    }

    private fun setBirthYear() {
        itemBinding.tvCharacterDetailsBirthYear.text =
            resources.getString(R.string.birthYear, character!!.birthYear)
    }

    private fun setEyeColor() {
        itemBinding.tvCharacterDetailsEyeColor.text =
            resources.getString(R.string.eyeColor, character!!.eyeColor)
    }

    private fun setSkinColor() {
        itemBinding.tvCharacterDetailsSkinColor.text =
            resources.getString(R.string.skinColor, character!!.skinColor)
    }

    private fun setHairColor() {
        itemBinding.tvCharacterDetailsHairColor.text =
            resources.getString(R.string.hairColor, character!!.hairColor)
    }

    private fun setMass() {
        itemBinding.tvCharacterDetailsMass.text =
            resources.getString(R.string.mass, character!!.mass)
    }

    private fun setHeight() {
        itemBinding.tvCharacterDetailsHeight.text =
            resources.getString(R.string.height, character!!.height)
    }

    private fun setName() {
        itemBinding.tvCharacterDetailsName.text =
            resources.getString(R.string.name, character!!.name)
    }

    private fun setAvatar() {
        Glide.with(this).load(Constants.AVATARS[characterPhotoID!!])
            .into(binding.ivCharacterAvatarDetails)
    }

    override fun updateFilmList(film: Film) {
        itemBinding.tvCharacterDetailsFilms.text.appendMovie(film)
    }

    override fun displayNoFilmsMessage() {
        itemBinding.tvCharacterDetailsFilms.text =
            getString(R.string.no_films_error)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun showProgress() {
        binding.progressBarLayoutDetails.root.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarLayoutDetails.root.visibility = View.GONE
    }

    override fun displayNoInternetMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.error_no_internet),
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun CharSequence.appendMovie(film: Film) {
        itemBinding.tvCharacterDetailsFilms.text =
            resources.getString(R.string.films, this, film.title)
    }

    override fun onNetworkAvailable() {
        if (this::itemBinding.isInitialized && !isDataCached())
            runOnUiThread { populateMovieList() }
    }

    override fun isDataCached(): Boolean {
        if (hasMovies()) return true
        return false
    }

    override fun hasMovies() =
        itemBinding.tvCharacterDetailsFilms.text != getString(R.string.no_films_error)

    override fun hasErrorNoMovie() =
        itemBinding.tvCharacterDetailsFilms.text == getString(R.string.no_films_error)

    override fun clearFilms() {
        itemBinding.tvCharacterDetailsFilms.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }
}