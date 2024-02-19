package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoanDTO(
    @SerialName("id")
    val loanId: Long,
    @SerialName("member_id")
    val memberId: Long,
    @SerialName("chamaa_account_id")
    val chamaAccountId: Long,
    @SerialName("loan_amount")
    val loanAmount: Double,
    @SerialName("interest_rate")
    val interestRate: Double,
    @SerialName("loan_date")
    val loanDate: String,
    @SerialName("loan_status")
    val loanStatus: String
)
