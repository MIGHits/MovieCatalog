package com.example.moviecatalog.presentation.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.WelcomeScreenBinding

class WelcomeScreen:Fragment(R.layout.welcome_screen) {
    private var binding:WelcomeScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = WelcomeScreenBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding?.accountEntry?.setOnClickListener{navigateAccountEntrance()}
        binding?.accountRegistration?.setOnClickListener{navigateRegistration()}
    }

    private fun navigateAccountEntrance(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcome_screen_layer,SignInScreen())
            .commit()
    }

    private  fun navigateRegistration(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcome_screen_layer,SignUpScreen())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}