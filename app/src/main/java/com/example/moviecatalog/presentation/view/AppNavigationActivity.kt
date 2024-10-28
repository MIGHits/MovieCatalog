package com.example.moviecatalog.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ActivityAppNavigationBinding
import com.example.moviecatalog.presentation.view.MainActivity.Companion.addKeybordListener
import com.example.moviecatalog.presentation.view.navigationBarFragments.FeedScreen
import com.example.moviecatalog.presentation.view.navigationBarFragments.MovieScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class AppNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_navigation)
        binding = ActivityAppNavigationBinding.inflate(layoutInflater)

        val rootView = binding.root
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FeedScreen())
                        .commit()
                    true
                }

                R.id.films -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieScreen())
                        .commit()
                    true
                }

                R.id.library -> {
                    println("3")
                    true
                }

                R.id.profile -> {
                    println("4")
                    true
                }

                else -> false
            }
        }

        MainActivity.hideNavigationBar(this.window)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FeedScreen())
                .commit()

            addKeybordListener(rootView, this.window)
        }
    }
}
