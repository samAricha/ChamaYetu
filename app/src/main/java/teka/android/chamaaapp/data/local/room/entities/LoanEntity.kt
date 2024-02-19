package teka.android.chamaaapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val loanId: String = generateUniqueId(),
    val chamaAccountId: Long,
    val memberId: Long,
    val loanAmount: Double,
    val interestRate: Double,
    val loanDate: String,
    val loanStatus: String
){
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}