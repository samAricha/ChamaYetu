package teka.android.chamaaapp.domain.mappers

import teka.android.chamaaapp.data.local.room.entities.AccountTypeEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.AccountTypeDTO
import teka.android.chamaaapp.data.remote.dtos.MemberDTO

fun AccountTypeDTO.toAccountTypeEntity(): AccountTypeEntity {
    return AccountTypeEntity(
        accountTypeId = this.accountTypeId,
        accountName = this.accountName
    )
}