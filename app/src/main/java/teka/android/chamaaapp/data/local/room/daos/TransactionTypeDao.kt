package teka.android.chamaaapp.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.TransactionEntity
import teka.android.chamaaapp.data.local.room.entities.TransactionTypeEntity


@Dao
interface TransactionTypeDao {

    @Query("SELECT * FROM transaction_types")
    fun getAllTransactionTypes(): Flow<List<TransactionTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionType(transactionType: TransactionTypeEntity)

}
