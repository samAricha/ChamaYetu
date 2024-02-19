package teka.android.chamaaapp.data.models

import kotlinx.serialization.Serializable
import teka.android.chamaaapp.data.local.room.entities.MemberEntity

@Serializable
data class MemberRequest(
    val firstName: String,
    val lastName: String,
    val contactInformation: String,
    val dateJoined: String,
)


fun MemberEntity.toMemberRequest(): MemberRequest {
    return MemberRequest(
        firstName = this.firstName,
        lastName = this.lastName,
        contactInformation = this.contactInformation,
        dateJoined = this.dateJoined,
    )
}