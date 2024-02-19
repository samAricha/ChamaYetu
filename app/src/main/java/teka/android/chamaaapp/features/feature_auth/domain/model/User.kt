package teka.android.chamaaapp.features.feature_auth.domain.model

import teka.android.chamaaapp.features.feature_auth.data.dto.Address
import teka.android.chamaaapp.features.feature_auth.data.dto.Name

data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
)
