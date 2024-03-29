package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.AccountTypeDTO
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface AccountTypeRetrofitService {

    @GET("api/account_types")
    suspend fun getAccountTypes():ApiResponseHandler<List<AccountTypeDTO>>

    @POST("api/account_types")
    suspend fun backupAccountTypes(@Body accountTypeDTO : AccountTypeDTO): ApiResponseHandler<AccountTypeDTO>

}
