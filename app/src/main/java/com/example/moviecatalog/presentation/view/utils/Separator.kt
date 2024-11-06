package com.example.moviecatalog.presentation.view.utils

import java.text.DecimalFormatSymbols
import java.util.Locale

object Separator {

    val symbols = DecimalFormatSymbols(Locale.getDefault())
    val df = java.text.DecimalFormat("#.#", symbols)

    operator fun invoke(rating: Double): String {
        symbols.decimalSeparator = '.'
        return df.format(rating)
    }
}