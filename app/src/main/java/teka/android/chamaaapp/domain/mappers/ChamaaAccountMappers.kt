package teka.android.chamaaapp.domain.mappers

import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ChamaAccountDTO
import teka.android.chamaaapp.data.remote.dtos.ChamaDTO
import teka.android.chamaaapp.data.remote.dtos.MemberDTO

fun ChamaAccountEntity.toChamaaAccountDto(): ChamaAccountDTO {
    return ChamaAccountDTO(
        chamaAccountId = this.accountId.toLong(),
        chamaId = this.chamaId.toLong(),
        accountName = this.accountName,
        accountTypeId = this.accountTypeId,
//        dateFormed = this.dateFormed
    )
}