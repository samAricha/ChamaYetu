package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.models.MemberRequest
import teka.android.chamaaapp.data.remote.dtos.ApiResponseHandler
import teka.android.chamaaapp.data.remote.dtos.MemberDTO
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface MemberRetrofitService {
    @GET("api/chamaa_members")
    suspend fun getChamaaMembers():ApiResponseHandler<List<MemberDTO>>

    @POST("api/chamaa_members")
    suspend fun backupMemberEntity(@Body memberDTO: MemberDTO): ApiResponseHandler<MemberDTO>

}
