package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.common.Constants.LOGIN_ERROR
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidateLoginUseCase {
    operator fun invoke(login:String):ValidationStatus{
        return if(login.isNotEmpty() && login.count{it.isLetter()}>=2){
            ValidationStatus(
                status = true,
                errorState = View.GONE
            )
        }else{
            ValidationStatus(
                status = false,
                errorMessage = LOGIN_ERROR,
                errorState = View.VISIBLE
            )
        }
    }
}