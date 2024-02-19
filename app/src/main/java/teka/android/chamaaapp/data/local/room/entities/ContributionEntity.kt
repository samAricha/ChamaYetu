package teka.android.chamaaapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "contributions")
data class ContributionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val contributionId: String = generateUniqueId(),
    val memberId: String,
    val chamaAccountId: String,
    val contributionDate: String,
    val contributionAmount: String,
    var isBackedUp: Boolean = false
){
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}