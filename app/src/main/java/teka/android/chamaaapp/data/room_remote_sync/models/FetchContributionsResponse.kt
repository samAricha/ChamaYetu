package teka.android.chamaaapp.data.room_remote_sync.models

import kotlinx.serialization.Serializable
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity

@Serializable
data class FetchContributionsResponse(
    val page: Int? = null,
    val results: List<ContributionsListItem>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class ContributionsListItem(
    val contributionId: String,
    val memberId: String,
    val chamaAccountId: String,
    val contributionDate: String,
    val contributionAmount: String,
)

fun ContributionsListItem.toContributionEntity(): ContributionEntity {
    return ContributionEntity(
        contributionId = this.contributionId,
        memberId = this.memberId,
        chamaAccountId = this.chamaAccountId,
        contributionDate = this.contributionDate,
        contributionAmount = this.contributionAmount,
    )
}