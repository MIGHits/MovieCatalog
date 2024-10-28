package com.example.moviecatalog.presentation.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalog.MovieCatalogApplication
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.PREFS_NAME
import com.example.moviecatalog.common.Constants.TOKEN_KEY
import com.example.moviecatalog.presentation.view.main.WelcomeScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootView = findViewById<View>(android.R.id.content)
        hideNavigationBar(this.window)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WelcomeScreen())
                .commit()
            enableEdgeToEdge()
            addKeybordListener(rootView, this.window)
        }

    }

    companion object {
        fun hideNavigationBar(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.let { controller ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        controller.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        controller.systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }
            }
        }

        fun showNavigationBar(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.let { controller ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        controller.show(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
                    }
                }
            }
        }

        fun addKeybordListener(rootView: View, window: Window) {
            rootView.setOnApplyWindowInsetsListener { _, insets ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                    insets.isVisible(WindowInsets.Type.ime())
                ) {
                    showNavigationBar(window)
                } else {
                    hideNavigationBar(window)
                }
                insets
            }
        }
    }
}