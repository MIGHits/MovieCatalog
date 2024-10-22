package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.common.Constants.PASSWORD_CONFIRM_ERROR
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidatePasswordConfirmUseCase {
    operator fun invoke(passwordConfirm:String,password:String):ValidationStatus{
        return if(passwordConfirm == password){
            ValidationStatus(
                status = true,
                errorState = View.GONE
            )
        }else{
            ValidationStatus(
                status = false,
                errorMessage = PASSWORD_CONFIRM_ERROR,
                errorState = View.VISIBLE
            )
        }
    }
}