package teka.android.chamaaapp.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity

@Dao
interface ChamaDao {
    @Query("SELECT * FROM chamas")
    fun getAllChamas(): Flow<List<ChamaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chama: ChamaEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(chamaa: ChamaEntity)

}
