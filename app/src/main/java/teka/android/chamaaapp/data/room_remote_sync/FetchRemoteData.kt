package teka.android.chamaaapp.data.room_remote_sync

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.AccountTypeEntity
import teka.android.chamaaapp.data.remote.dtos.AccountTypeDTO
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.networking.RemoteRetrofitProvider
import teka.android.chamaaapp.domain.mappers.toAccountTypeEntity
import teka.android.chamaaapp.util.ResultResource

class FetchRemoteData {

    suspend fun fetchAndSaveAccountTypes(repository: DbRepository): ResultResource<Unit>{
        return withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
                val response: ApiResponseHandler<List<AccountTypeDTO>> = RemoteRetrofitProvider.createAccountTypesRetrofitService().getAccountTypes()
                if(response.isSuccessful){
                    val accountTypes: List<AccountTypeEntity> = response.data!!.map { it.toAccountTypeEntity() }
                    val repositoryResponse = repository.insertAccountTypes(accountTypes)
                    Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())
                    // Return success with data
                    ResultResource.Success(repositoryResponse)
                }else{
                    ResultResource.Error("Failed to fetch account types: ${response.message}")
                }

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())
                ResultResource.Error("Error fetching account types: ${e.message}")
            }
        }
    }

}