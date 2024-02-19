package teka.android.chamaaapp.features.feature_auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val username: String
)