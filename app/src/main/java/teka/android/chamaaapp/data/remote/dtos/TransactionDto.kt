package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDTO(
    @SerialName("id")
    val transactionId: Long,
    @SerialName("member_id")
    val memberId: Long,
    @SerialName("chamaa_account_id")
    val chamaAccountId: Long,
    @SerialName("transaction_date")
    val transactionDate: String,
    @SerialName("transaction_type_id")
    val transactionType: String,
    val amount: Double
)
