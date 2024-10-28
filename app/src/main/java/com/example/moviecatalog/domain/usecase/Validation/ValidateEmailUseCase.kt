package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.common.Constants.EMAIL_ERROR
import com.example.moviecatalog.common.Constants.EMAIL_PATTERN
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationStatus {
        return if (email.matches(EMAIL_PATTERN.toRegex())) {
            ValidationStatus(
                status = true,
                errorState = View.GONE
            )
        } else {
            ValidationStatus(
                status = false,
                errorMessage = EMAIL_ERROR,
                errorState = View.VISIBLE
            )
        }
    }
}