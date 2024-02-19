package teka.android.chamaaapp.presentation.profile.data

import teka.android.chamaaapp.presentation.profile.domain.model.User
import teka.android.chamaaapp.features.feature_auth.data.dto.UserResponseDto

internal fun UserResponseDto.toDomain(): User {
    return User(
        address = address,
        email = email,
        id = id,
        name = name,
        password = password,
        phone = phone,
        username = username
    )
}