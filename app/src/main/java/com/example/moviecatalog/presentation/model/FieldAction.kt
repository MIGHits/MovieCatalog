package com.example.moviecatalog.presentation.model

import android.widget.EditText

data class FieldAction(
    val editText: EditText,
    val fieldName: String? = null,
    val action: (String) -> Unit
)
