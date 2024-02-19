package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.LoanDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface LoanRetrofitService {


    @GET("api/loans")
    suspend fun getLoans():ApiResponseHandler<List<LoanDTO>>


    @POST("api/loans")
    suspend fun backupLoanEntity(@Body loanDTO: LoanDTO): ApiResponseHandler<LoanDTO>

}
