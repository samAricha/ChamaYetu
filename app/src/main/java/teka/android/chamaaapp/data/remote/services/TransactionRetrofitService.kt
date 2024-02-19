package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.TransactionDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface TransactionRetrofitService {


    @GET("api/members/fetch/all")
    suspend fun getTransactions():ApiResponseHandler<List<TransactionDTO>>

    @POST("api/save/member")
    suspend fun backupTransactionEntity(@Body transactionDTO: TransactionDTO): ApiResponseHandler<TransactionDTO>

}
