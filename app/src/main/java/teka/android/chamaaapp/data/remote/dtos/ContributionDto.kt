package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContributionDTO(
    @SerialName("id")
    val contributionId: Long,
    @SerialName("member_id")
    val memberId: Long,
    @SerialName("chamaa_account_id")
    val chamaAccountId: Long,
    @SerialName("contribution_date")
    val contributionDate: String,
    @SerialName("contribution_amount")
    val contributionAmount: Double
)
