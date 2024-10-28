package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.common.Constants.EMPTY_FIELD_ERROR
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidateNameField {
    operator fun invoke(textField: String): ValidationStatus {
        return if (textField.isEmpty()) {
            ValidationStatus(
                status = false,
                errorMessage = EMPTY_FIELD_ERROR,
                errorState = View.VISIBLE
            )
        } else {
            ValidationStatus(
                status = true,
                errorState = View.GONE
            )
        }
    }
}