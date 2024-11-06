package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.DAY_GREETINGS
import com.example.moviecatalog.common.Constants.EVENING_GREETINGS
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.MORNING_GREETINGS
import com.example.moviecatalog.common.Constants.NIGHT_GREETINGS
import com.example.moviecatalog.databinding.ProfileScreenBinding
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.view.MainActivity
import com.example.moviecatalog.presentation.view.utils.AvatarChangeDialog
import com.example.moviecatalog.presentation.view.utils.DialogResult
import com.example.moviecatalog.presentation.view_model.ProfileViewModel
import com.example.moviecatalog.presentation.view_model.ProfileViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class ProfileScreen : Fragment(R.layout.profile_screen), DialogResult {
    private var binding: ProfileScreenBinding? = null
    private lateinit var viewModel: ProfileViewModel
    private lateinit var time: String
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
        val bundle = Bundle()
        binding?.profileAvatar?.setOnClickListener {
            avatarDialog.show(childFragmentManager, null)
        }

        binding?.logoutButton?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout().join()
                logoutNavigate()
            }
        }

        lifecycleScope.launch {
            viewModel.getProfile().join()
            subscribeProfile()
            viewModel.getCurrentTime()
            subscribeTime()
        }

        binding?.friendsAvatars?.setOnClickListener {
            bundle.putString("userId", profile.id)
            findNavController().navigate(
                R.id.action_profileScreen_to_friendsScreen, bundle
            )
        }
    }


    private fun subscribeProfile() = lifecycleScope.launch {
        viewModel.profile.collect { profileData ->
            profile = profileData
            updateUIState()
        }
    }

    private fun subscribeTime() = lifecycleScope.launch {
        viewModel.time.collect { currentTime ->
            time = currentTime
            updateTimeUI(time)
        }
    }

    private fun updateUIState() {
        binding?.apply {
            profileLogin.text = profile.nickName
            profileEmail.text = profile.email
            profileName.text = profile.name
            personName.text = profile.name
            profileBirthDate.text = profile.birthDate

            if (profile.avatarLink != null &&
                profile.avatarLink != INITIAL_FIELD_STATE
            ) {
                Picasso.get()
                    .load(profile.avatarLink)
                    .fit()
                    .centerCrop()
                    .into(profileAvatar)
            }

            when (profile.gender) {
                1 -> {
                    binding?.female?.setBackgroundResource(R.drawable.gender_button_primary)
                    binding?.male?.setBackgroundResource(R.color.DarkFaded)
                }
            }
        }
    }

    private fun updateTimeUI(currentHour: String) {
        val parcedTime = currentHour.toInt()
        val greetings = binding?.greetings

        when (parcedTime) {
            in 6..11 -> greetings?.text = MORNING_GREETINGS
            in 12..17 -> greetings?.text = DAY_GREETINGS
            in 18..23 -> greetings?.text = EVENING_GREETINGS
            in 0..5 -> greetings?.text = NIGHT_GREETINGS
        }

    }

    private fun logoutNavigate() {
        findNavController().navigate(R.id.action_profileScreen_to_mainActivity)
        activity?.finish()
    }

    override fun onDialogResult(result: String) {
        lifecycleScope.launch {
            viewModel.updateUserProfile(result).join()
            viewModel.getProfile().join()
        }
    }
}