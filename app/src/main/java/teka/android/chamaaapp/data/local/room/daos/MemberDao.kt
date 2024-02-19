package teka.android.chamaaapp.data.local.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.MemberNameSemiEntity

@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: MemberEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(member: MemberEntity)

    @Query("SELECT firstName, lastName FROM members WHERE memberId=:memberId")
    fun getMemberById(memberId:String): Flow<MemberNameSemiEntity>

    @Query("SELECT COUNT(*) FROM members")
    fun getMemberCount(): Flow<Int>

}
