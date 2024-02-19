package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.ChamaDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface ChamaaRetrofitService {

    @GET("api/chamaas")
    suspend fun getAllChamaas():ApiResponseHandler<List<ChamaDTO>>

    @POST("api/chamaas")
    suspend fun backupChamaaEntity(@Body chamaDTO: ChamaDTO): ApiResponseHandler<ChamaDTO>

}
