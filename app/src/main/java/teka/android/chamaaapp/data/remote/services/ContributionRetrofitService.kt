package teka.android.chamaaapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.models.ContributionRequest
import teka.android.chamaaapp.data.room_remote_sync.models.FetchContributionsResponse
import teka.android.chamaaapp.data.room_remote_sync.models.FetchMembersResponse
import teka.android.chamaaapp.data.room_remote_sync.models.UploadDataResponse

interface ContributionRetrofitService {

    @GET("api/contributions")
    suspend fun getContributionList():FetchContributionsResponse


    @POST("api/contributions")
    suspend fun backupContributionEntity(
        @Body contributionRequest : ContributionRequest
    ): UploadDataResponse

}
