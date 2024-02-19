package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TransactionTypeDTO(
    @SerialName("id")
    val transactionTypeId: Long,
    @SerialName("name")
    val transactionName: String,
)
