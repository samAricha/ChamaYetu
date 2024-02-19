package teka.android.chamaaapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import java.util.UUID

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transactionId: String = generateUniqueId(),
    val memberId: Long,
    val chamaAccountId: Long,
    val transactionDate: String,
    val transactionType: String,
    val amount: Double
){
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}