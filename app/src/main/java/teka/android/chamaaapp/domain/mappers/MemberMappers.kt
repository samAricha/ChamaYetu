package teka.android.chamaaapp.domain.mappers

import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.MemberDTO

fun MemberEntity.toMemberDto(): MemberDTO {
    return MemberDTO(
        memberId = this.memberId,
        firstName = this.firstName,
        lastName = this.lastName,
        contactInformation = this.contactInformation,
        dateJoined = this.dateJoined,
    )
}