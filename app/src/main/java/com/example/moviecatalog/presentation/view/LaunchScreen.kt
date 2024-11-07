package com.example.moviecatalog.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.LaunchScreenBinding
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.view.MainActivity.Companion.addKeybordListener
import com.example.moviecatalog.presentation.view.MainActivity.Companion.hideNavigationBar
import com.example.moviecatalog.presentation.view_model.LaunchViewModel
import com.example.moviecatalog.presentation.view_model.LaunchViewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LaunchScreen : AppCompatActivity() {
    private var binding: LaunchScreenBinding? = null
    private lateinit var viewModel: LaunchViewModel
    private var userState: Boolean = false
    private lateinit var user: ProfileModelUI
    private val bundle = Bundle()
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, LaunchViewModelFactory()).get(LaunchViewModel::class.java)
        setContentView(R.layout.launch_screen)
        binding = LaunchScreenBinding.inflate(layoutInflater)
        val rootView = binding?.root
        hideNavigationBar(this.window)

        if (savedInstanceState == null) {
            enableEdgeToEdge()
            rootView?.let { addKeybordListener(it, this.window) }
        }
        subscribeUserState()
    }

    fun navigateToFeed() {
        intent = Intent(this, AppNavigationActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    fun navigateToWelcome() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    401 -> {
                        navigateToWelcome()
                    }
                }
            }

            else -> {
                navigateToFeed()
            }
        }
    }

    fun succes() {
        lifecycleScope.launch(exceptionHandler) {
            bundle.putString("userId", user.id)
            navigateToFeed()
        }
    }

    fun subscribeUserState() {
        lifecycleScope.launch {
            viewModel.isUserLogin.collect { check ->
                userState = check
                if (userState) {
                    viewModel.getProfileData().join()
                    subscribeUserData()
                } else {
                    navigateToWelcome()
                }
            }
        }
    }

    fun subscribeUserData() {
        lifecycleScope.launch {
            viewModel.userProfile.collect { profile ->
                user = profile
                succes()
            }
        }
    }

}