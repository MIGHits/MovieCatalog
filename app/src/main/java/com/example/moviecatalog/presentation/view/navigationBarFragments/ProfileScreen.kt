package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ProfileScreenBinding
import com.example.moviecatalog.domain.entity.ProfileModel
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.view_model.ProfileViewModel
import com.example.moviecatalog.presentation.view_model.ProfileViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


class ProfileScreen() :
    Fragment(R.layout.profile_screen), DialogResult {
    private lateinit var binding: ProfileScreenBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var profile: ProfileModelUI
    private lateinit var avatarDialog: AvatarChangeDialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        avatarDialog =
            AvatarChangeDialog(requireContext(), this)
        viewModel =
            ViewModelProvider(this, ProfileViewModelFactory())
                .get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileScreenBinding.bind(view)
        binding.profileAvatar.setOnClickListener {
            avatarDialog.show(childFragmentManager, null)
        }

        lifecycleScope.launch {
            subscribeProfile().join()
            updateUIState()
        }
    }


    fun subscribeProfile() = lifecycleScope.launch {
        viewModel.getProfile().join()
        viewModel.profile.take(1).collect { profileData ->
            profile = profileData
        }
    }

    private fun updateUIState() {
        binding.apply {
            profileLogin.text = profile.nickName
            profileEmail.text = profile.email
            profileName.text = profile.name
            personName.text = profile.name
            profileBirthDate.text = profile.birthDate
            if (profile.avatarLink != null && profile.avatarLink!!.isNotEmpty()) {
                Picasso.get()
                    .load(profile.avatarLink)
                    .fit()
                    .centerCrop()
                    .into(profileAvatar)
            }
        }
    }

    override fun onDialogResult(result: String) {
        viewModel.updateUserProfile(result)
    }
}