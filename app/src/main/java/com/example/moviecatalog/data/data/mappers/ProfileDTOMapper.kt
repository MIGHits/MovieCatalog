package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.ProfileModelDTO
import com.example.moviecatalog.domain.entity.ProfileModel

class ProfileDTOMapper {
    fun map(profile: ProfileModel):ProfileModelDTO{
        return ProfileModelDTO(
            id = profile.id,
            nickName = profile.nickName,
            email = profile.email,
            avatarLink = profile.avatarLink,
            name = profile.name,
            birthDate = profile.birthDate,
            gender = profile.gender
        )
    }
}