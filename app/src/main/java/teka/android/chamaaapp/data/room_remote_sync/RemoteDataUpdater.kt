package teka.android.chamaaapp.data.room_remote_sync

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.toContributionRequest
import teka.android.chamaaapp.data.models.toMemberRequest
import teka.android.chamaaapp.data.remote.networking.RemoteRetrofitProvider
import teka.android.chamaaapp.domain.mappers.toChamaaAccountDto
import teka.android.chamaaapp.domain.mappers.toChamaaDto
import teka.android.chamaaapp.domain.mappers.toMemberDto

import javax.inject.Inject


sealed class UpdateResult {
    data class Success(val message: String) : UpdateResult()
    data class Failure(val errorMessage: String) : UpdateResult()
}

class RemoteDataUpdater @Inject constructor(
    private val appContext: Context
) {

    suspend fun backupMemberData(
        memberEntityList: List<MemberEntity>,
        repository: DbRepository
    ): UpdateResult {

        return try{
            withContext(Dispatchers.IO) {
                memberEntityList.forEach { memberEntity ->
                    val memberDTO = memberEntity.toMemberDto()

                        val response = RemoteRetrofitProvider
                            .createMemberRetrofitService()
                            .backupMemberEntity(memberDTO)
                    if (response.isSuccessful){
                        memberEntity.isBackedUp = true
                        repository.updateMemberEntity(memberEntity)
//                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                        UpdateResult.Success("Data updated successfully.")
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        } catch (e: Exception) {
                e.printStackTrace()
                Log.d(">>>>>>>>REMOTEEGG", e.message.toString())
            UpdateResult.Failure("Error updating data: ${e.message}")
        }
    }

    suspend fun backupContributionData(
        contributionEntityList: List<ContributionEntity>,
        repository: DbRepository
    ): UpdateResult {

        return try{
            withContext(Dispatchers.IO) {
                contributionEntityList.forEach { contributionEntity ->
                    val contributionRequest = contributionEntity.toContributionRequest()

                    val response = RemoteRetrofitProvider
                        .createContributionRetrofitService()
                        .backupContributionEntity(contributionRequest)
                    if (response.success){
                        contributionEntity.isBackedUp = true
                        repository.updateContributionEntity(contributionEntity)
//                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                        UpdateResult.Success("Data updated successfully.")
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(">>>>>>>>REMOTEEGG", e.message.toString())
            UpdateResult.Failure("Error updating data: ${e.message}")
        }
    }

    suspend fun backupChamaasAndAccountData(
        chamaaList: List<ChamaEntity>,
        chamaaAccountList: List<ChamaAccountEntity>,
        repository: DbRepository
    ): UpdateResult {

        return try{
            withContext(Dispatchers.IO) {
                chamaaList.forEach { chamaaEntity ->
                    val chamaaDto = chamaaEntity.toChamaaDto()

                    val response = RemoteRetrofitProvider
                        .createChamaasRetrofitService()
                        .backupChamaaEntity(chamaaDto)
                    if (response.isSuccessful){
                        chamaaEntity.isBackedUp = true
                        repository.updateChamaaEntity(chamaaEntity)
//                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                        UpdateResult.Success("Data updated successfully.")
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }

                chamaaAccountList.forEach { chamaaAccountEntity ->
                    val chamaaAccountDto = chamaaAccountEntity.toChamaaAccountDto()

                    val response = RemoteRetrofitProvider
                        .createChamaaAccountsRetrofitService()
                        .backupChamaaAccounts(chamaaAccountDto)
                    if (response.isSuccessful){
                        chamaaAccountEntity.isBackedUp = true
                        repository.updateChamaaAccountEntity(chamaaAccountEntity)
//                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                        UpdateResult.Success("Data updated successfully.")
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(">>>>>>>>REMOTEEGG", e.message.toString())
            UpdateResult.Failure("Error updating data: ${e.message}")
        }
    }

}