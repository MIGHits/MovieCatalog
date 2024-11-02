package com.example.moviecatalog.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ActivityAppNavigationBinding
import com.example.moviecatalog.presentation.view.MainActivity.Companion.addKeybordListener
import com.example.moviecatalog.presentation.view.navigationBarFragments.FeedScreen
import com.example.moviecatalog.presentation.view.navigationBarFragments.MovieScreen
import com.example.moviecatalog.presentation.view.navigationBarFragments.ProfileScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class AppNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppNavigationBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_navigation)
        binding = ActivityAppNavigationBinding.inflate(layoutInflater)
        val rootView = binding.root
        //Через binding navigation bar не работает))
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.movie_navigation_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null

        MainActivity.hideNavigationBar(this.window)

        if (savedInstanceState == null) {
            addKeybordListener(rootView, this.window)
        }
    }
}
