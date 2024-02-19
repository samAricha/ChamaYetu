package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.ChamaAccountDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface ChamaaAccountRetrofitService {

    @GET("api/chamaa_accounts")
    suspend fun getChamaaAccounts():ApiResponseHandler<List<ChamaAccountDTO>>


    @POST("api/chamaa_accounts")
    suspend fun backupChamaaAccounts(@Body chamaAccountDTO: ChamaAccountDTO): ApiResponseHandler<ChamaAccountDTO>

}
