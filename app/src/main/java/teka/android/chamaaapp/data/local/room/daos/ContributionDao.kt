package teka.android.chamaaapp.data.local.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionWithMemberName



@Dao
interface ContributionDao {
    @Query("SELECT * FROM contributions")
    fun getAllContributions(): Flow<List<ContributionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contribution: ContributionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contribution: ContributionEntity)

    @Query("SELECT contributions.*, members.firstName AS memberFirstName, members.lastName AS memberLastName FROM contributions JOIN members ON contributions.memberId = members.memberId")
    fun getAllContributionsWithMemberNames(): Flow<List<ContributionWithMemberName>>


}
