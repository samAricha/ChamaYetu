package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvestmentDTO(
    @SerialName("id")
    val investmentId: Long,
    @SerialName("member_id")
    val memberId: Long,
    @SerialName("chamaa_account_id")
    val chamaAccountId: Long,
    @SerialName("investment_description")
    val investmentDescription: String,
    @SerialName("investment_amount")
    val investmentAmount: Double,
    @SerialName("investment_date")
    val investmentDate: String
)
