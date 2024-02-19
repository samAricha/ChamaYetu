package teka.android.chamaaapp.features.feature_auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
    @SerialName("__v")
    val v: Int
)