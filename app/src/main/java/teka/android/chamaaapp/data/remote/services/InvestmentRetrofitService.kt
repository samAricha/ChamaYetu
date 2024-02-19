package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.InvestmentDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface InvestmentRetrofitService {


    @GET("api/investments")
    suspend fun getAllInvestments():ApiResponseHandler<InvestmentDTO>


    @POST("api/investments")
    suspend fun backupInvestmentEntity(@Body investmentDTO: InvestmentDTO): ApiResponseHandler<InvestmentDTO>

}
