package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.ProfileModel
import com.example.moviecatalog.presentation.entity.ProfileModelUI

class ProfileUIMapper {
    fun map(profile:ProfileModel):ProfileModelUI{
        return ProfileModelUI(
            id  = profile.id,
            nickName = profile.nickName,
            email = profile.email,
            avatarLink = profile.avatarLink,
            name = profile.name,
            birthDate = profile.birthDate,
            gender = profile.gender
        )
    }

    fun reverseMap(profile: ProfileModelUI):ProfileModel{
        return ProfileModel(
            id  = profile.id,
            nickName = profile.nickName,
            email = profile.email,
            avatarLink = profile.avatarLink,
            name = profile.name,
            birthDate = profile.birthDate,
            gender = profile.gender
        )
    }
}