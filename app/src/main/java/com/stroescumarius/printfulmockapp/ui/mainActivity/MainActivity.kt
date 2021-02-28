package com.stroescumarius.printfulmockapp.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.stroescumarius.printfulmockapp.R
import com.stroescumarius.printfulmockapp.databinding.ActivityMainBinding
import com.stroescumarius.printfulmockapp.models.Character
import com.stroescumarius.printfulmockapp.models.Variables
import com.stroescumarius.printfulmockapp.ui.base.BaseActivity
import com.stroescumarius.printfulmockapp.ui.characterDetails.CharacterDetails
import com.stroescumarius.printfulmockapp.utils.constants.Constants

class MainActivity : BaseActivity(), MainActivityContract.View {
    private var presenter: MainActivityPresenter? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        initPresenter()
        downloadCharacters()
        initRecycler()
    }

    private fun setupViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initPresenter() {
        presenter = MainActivityPresenter(this)
    }

    private fun downloadCharacters() {
        presenter?.downloadCharacters()
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = CharacterAdapter { character, position ->
            onItemClick(character, position)
        }
        setLayoutManager(layoutManager)
        setAdapter(adapter)
    }

    private fun setLayoutManager(layoutManager: LinearLayoutManager) {
        binding.rvCharacters.layoutManager = layoutManager
    }

    private fun setAdapter(adapter: CharacterAdapter) {
        binding.rvCharacters.adapter = adapter
    }

    private fun onItemClick(character: Character, position: Int) {
        val intentCharacterDetails = Intent(this, CharacterDetails::class.java)
        addCharacterInfo(intentCharacterDetails, character, position)
        startActivity(intentCharacterDetails)
    }

    private fun addCharacterInfo(
        intentCharacterDetails: Intent,
        character: Character,
        position: Int
    ) {
        intentCharacterDetails.putExtra(Constants.CHARACTER, character)
        intentCharacterDetails.putExtra(Constants.CHARACTER_PHOTO_ID, position)
    }

    override fun showShadowDecoration() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.shadowRv.visibility = View.VISIBLE
        binding.shadowRv.startAnimation(fadeIn)
    }

    override fun updateRecycler(characters: MutableList<Character>) {
        val adapter = binding.rvCharacters.adapter as CharacterAdapter
        adapter.setData(characters)
        adapter.notifyDataSetChanged()
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun showProgress() {
        binding.progressBarLayout.root.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarLayout.root.visibility = View.GONE
    }

    override fun displayNoInternetMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.error_no_internet),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onNetworkAvailable() {
        if (this::binding.isInitialized && !isDataCached() && !isFirstTimeNetworkCheck()) {
            runOnUiThread { downloadCharacters() }
        }
    }

    private fun isFirstTimeNetworkCheck(): Boolean {
        return Variables.isFirstTimeNetworkCheck
    }

    override fun isDataCached(): Boolean {
        val adapter = binding.rvCharacters.adapter
        val numberOfCharaters = adapter?.itemCount
        if (numberOfCharaters != null && numberOfCharaters > 0) return true
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }
}