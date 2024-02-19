package teka.android.chamaaapp.data.models

import kotlinx.serialization.Serializable
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity

@Serializable
data class ContributionRequest(
    val memberId: String,
    val chamaAccountId: String,
    val contributionDate: String,
    val contributionAmount: String,
)

fun ContributionEntity.toContributionRequest(): ContributionRequest {
    return ContributionRequest(
        memberId = this.memberId,
        chamaAccountId = this.chamaAccountId,
        contributionDate = this.contributionDate,
        contributionAmount = this.contributionAmount,
    )
}