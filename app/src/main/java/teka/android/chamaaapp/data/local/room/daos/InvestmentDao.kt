package teka.android.chamaaapp.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.InvestmentEntity


@Dao
interface InvestmentDao {

    @Query("SELECT * FROM investments")
    fun getAllInvestments(): Flow<List<InvestmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(investment: InvestmentEntity)

}
