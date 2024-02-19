package teka.android.chamaaapp.data.room_remote_sync.models

import kotlinx.serialization.Serializable
import teka.android.chamaaapp.data.local.room.entities.MemberEntity

@Serializable
data class FetchMembersResponse(
    val page: Int? = null,
    val results: List<MembersListItem>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class MembersListItem(
    val memberId: String,
    val firstName: String,
    val lastName: String,
    val contactInformation: String,
    val dateJoined: String,
)

fun MembersListItem.toMembersEntity(): MemberEntity {
    return MemberEntity(
         memberId = this.memberId,
         firstName = this.firstName,
         lastName = this.lastName,
         contactInformation = this.contactInformation,
         dateJoined = this.dateJoined,
    )
}