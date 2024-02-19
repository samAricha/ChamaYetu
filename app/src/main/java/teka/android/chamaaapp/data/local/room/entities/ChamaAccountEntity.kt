package teka.android.chamaaapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "chama_accounts")
data class ChamaAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val accountId: String = generateUniqueId(),
    val chamaId: String,
    val accountName: String,
    val accountTypeId: String,
    var isBackedUp: Boolean = false
){
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}
