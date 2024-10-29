package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.ProfileModelDTO
import com.example.moviecatalog.domain.entity.ProfileModel

class ProfileMapper {
    fun map(profile:ProfileModelDTO):ProfileModel{
        return ProfileModel(
            id = profile.id,
            nickName = profile.nickName,
            email = profile.email,
            avatarLink = profile.avatarLink,
            name = profile.name,
            birthDate = profile.birthDate+"Z",
            gender = profile.gender
        )
    }
}