package teka.android.chamaaapp.domain.mappers

import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ChamaDTO
import teka.android.chamaaapp.data.remote.dtos.MemberDTO

fun ChamaEntity.toChamaaDto(): ChamaDTO {
    return ChamaDTO(
        chamaId = this.chamaId.toLong(),
        chamaName = this.chamaName,
        chamaDescription = this.chamaDescription,
        dateFormed = this.dateFormed
    )
}