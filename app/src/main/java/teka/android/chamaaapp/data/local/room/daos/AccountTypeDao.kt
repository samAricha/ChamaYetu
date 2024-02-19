package teka.android.chamaaapp.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.AccountTypeEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity

@Dao
interface AccountTypeDao {

    @Query("SELECT * FROM account_types")
    fun getAllAccountTypes(): Flow<List<AccountTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountType(accountType: AccountTypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountTypes(accountTypes: List<AccountTypeEntity>)

}
