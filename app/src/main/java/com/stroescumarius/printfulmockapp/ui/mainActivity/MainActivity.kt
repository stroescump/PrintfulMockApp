package com.stroescumarius.printfulmockapp.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.stroescumarius.printfulmockapp.R
import com.stroescumarius.printfulmockapp.data.constants.Constants
import com.stroescumarius.printfulmockapp.data.models.Character
import com.stroescumarius.printfulmockapp.data.models.Resource
import com.stroescumarius.printfulmockapp.data.models.Variables
import com.stroescumarius.printfulmockapp.databinding.ActivityMainBinding
import com.stroescumarius.printfulmockapp.ui.base.BaseActivity
import com.stroescumarius.printfulmockapp.ui.characterDetails.CharacterDetails
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupObservers()
        downloadCharacters()
        initRecycler()
    }

    private fun setupViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupObservers() {
        viewModel.downloadCharacters().observe(this) { onDataChanged(it) }
    }

    private fun onDataChanged(request: Resource<MutableList<Character>>) {
        when (request.status) {
            Resource.Status.SUCCESS -> {
                handleSuccess(request)
            }
            Resource.Status.ERROR -> {
                if (hasAvailableNetwork()) handleError(request, binding.root)
                else displayNoInternetMessage(binding.root)
                hideProgress()
            }
            Resource.Status.LOADING -> {
                showProgress()
            }
        }
    }

    private fun handleSuccess(resource: Resource<MutableList<Character>>) {
        hideProgress()
        hideRetryButton()
        resource.data?.let { it -> updateRecycler(it) }
        showShadowDecoration()
    }

    private fun hideRetryButton() {
        if (binding.layoutRetryBtn.btnRetryFetchData.visibility == View.VISIBLE)
            binding.layoutRetryBtn.btnRetryFetchData.visibility = View.GONE
    }

    private fun handleError(resource: Resource<Any>, view: View) {
        resource.message?.let { message ->
            displayErrorMessage(message, view)
            if (hasAvailableNetwork() && message == Constants.timeoutError) {
                setupRetryButton()
            }
        }

    }

    private fun setupRetryButton() {
        binding.layoutRetryBtn.btnRetryFetchData.visibility = View.VISIBLE
        binding.layoutRetryBtn.btnRetryFetchData.setOnClickListener {
            viewModel.downloadCharacters().observe(this@MainActivity) { onDataChanged(it) }
        }
    }

    private fun displayErrorMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    private fun downloadCharacters() {
        viewModel.downloadCharacters()
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

    private fun showShadowDecoration() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.shadowRv.visibility = View.VISIBLE
        binding.shadowRv.startAnimation(fadeIn)
    }

    private fun updateRecycler(characters: MutableList<Character>) {
        val adapter = binding.rvCharacters.adapter as CharacterAdapter
        adapter.setData(characters)
        adapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        binding.progressBarLayout.root.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarLayout.root.visibility = View.GONE
    }

    override fun displayNoInternetMessage(view: View) {
        Snackbar.make(
            view,
            getString(R.string.error_no_internet),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onNetworkAvailable() {
        if (this::binding.isInitialized && !isDataCached() && !isFirstTimeNetworkCheck()) {
            runOnUiThread { viewModel.downloadCharacters().observe(this) { onDataChanged(it) } }
        }
    }

    override fun isDataCached(): Boolean {
        val adapter = binding.rvCharacters.adapter
        val numberOfCharaters = adapter?.itemCount
        if (numberOfCharaters != null && numberOfCharaters > 0) return true
        return false
    }

    private fun isFirstTimeNetworkCheck(): Boolean {
        return Variables.isFirstTimeNetworkCheck
    }

}



