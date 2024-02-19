package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChamaAccountDTO(
    @SerialName("id")
    val chamaAccountId: Long,
    @SerialName("chama_id")
    val chamaId: Long,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("account_type_id")
    val accountTypeId: String,
)
