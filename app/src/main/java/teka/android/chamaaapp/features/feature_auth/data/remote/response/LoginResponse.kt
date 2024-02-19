package teka.android.chamaaapp.features.feature_auth.data.remote.response


import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)