package teka.android.chamaaapp.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity

@Dao
interface ChamaAccountDao {

    @Query("SELECT * FROM chama_accounts")
    fun getAllChamaAccounts(): Flow<List<ChamaAccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChamaAccount(chamaAccountEntity: ChamaAccountEntity)

    @Query("SELECT COUNT(*) FROM chama_accounts")
    fun getChamaaAccountsCount(): Flow<Int>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(chamaaAccount: ChamaAccountEntity)

}
