package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberDTO(
    @SerialName("id")
    val memberId: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("contact_information")
    val contactInformation: String,
    @SerialName("date_joined")
    val dateJoined: String
)
