package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidateBirthDateUseCase {
    operator fun invoke(birthDate: String): ValidationStatus {
        return if (birthDate.isNotEmpty()){
            ValidationStatus(
                status = true,
                errorState =  View.GONE
            )
        }else{
            ValidationStatus(
                status = false,
                errorMessage = "",
                errorState = View.VISIBLE
            )
        }
    }
}