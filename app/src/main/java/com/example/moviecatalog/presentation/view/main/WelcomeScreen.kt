package com.example.moviecatalog.presentation.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.WelcomeScreenBinding

class WelcomeScreen : Fragment(R.layout.welcome_screen) {
    private var binding: WelcomeScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = WelcomeScreenBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding?.accountEntry?.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeScreen_to_signInScreen)
        }
        binding?.accountRegistration?.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeScreen_to_signUpScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}