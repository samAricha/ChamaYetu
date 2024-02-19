package teka.android.chamaaapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "chamas")
data class ChamaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chamaId: String = generateUniqueId(),
    val chamaName: String,
    val chamaDescription: String,
    val dateFormed: String,
    var isBackedUp: Boolean = false
){
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}