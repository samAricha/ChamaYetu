package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountTypeDTO(
    @SerialName("id")
    val accountTypeId: Long = 0,
    @SerialName("name")
    val accountName: String,
    val created_at: String?,
    val updated_at: String?,
)
