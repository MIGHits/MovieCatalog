package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.common.Constants.BEARER
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage

class IsUserLoginUseCase() {
    operator fun invoke(): Boolean {
        return PrefsTokenStorage.getToken().token != BEARER
    }
}