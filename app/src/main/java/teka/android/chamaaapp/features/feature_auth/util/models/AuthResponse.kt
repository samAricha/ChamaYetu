package teka.android.chamaaapp.features.feature_auth.util.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val isSuccessful: Boolean,
    val message: String?,
    val data: AuthData,
    val errors: AuthErrors?,
    val code : Int
)

@Serializable
data class AuthData(
    val user: RegisteredUser,
    val access_token: String
)

@Serializable
data class AuthErrors(
    @SerialName("errors") val errors: Map<String, List<String>>?
)
@Serializable
data class LoginAuthResponse(
    val isSuccessful: Boolean,
    val message: String?,
    val status: String?,
    val data: AuthData? = null,
)
@Serializable
data class LoginAuthResponseError(
    val isSuccessful: Boolean,
    val message: String?,
    val status: String?,
    val data: String? = null,
)

@Serializable
data class RegisteredUser(
    val id: Int,
    val name: String,
    val email: String?,
    val phone: String?,
    val created_at: String,
    val updated_at: String
)

