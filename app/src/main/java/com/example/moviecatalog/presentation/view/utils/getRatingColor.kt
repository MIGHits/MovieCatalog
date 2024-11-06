package com.example.moviecatalog.presentation.view.utils

import android.graphics.Color
import com.example.moviecatalog.R

object getRatingColor {
    operator fun invoke(rating: Double): Int {
        return when {
            rating in 1.0..<2.3 -> R.color.rating1_0
            rating in 2.3..<3.6 -> R.color.rating2_3
            rating in 3.6..<4.0 -> R.color.rating3_6
            rating in 4.0..<6.0 -> R.color.rating4_0
            rating in 6.0..<7.4 -> R.color.rating6_0
            rating in 7.4..<8.5 -> R.color.rating7_4
            rating in 8.5..<9.9 -> R.color.rating8_5
            rating >= 9.9 -> R.color.rating9_9
            else -> {
                R.color.TextGray
            }
        }
    }
}