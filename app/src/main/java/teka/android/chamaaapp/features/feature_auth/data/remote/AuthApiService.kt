package teka.android.chamaaapp.features.feature_auth.data.remote

import teka.android.chamaaapp.features.feature_auth.data.dto.UserResponseDto
import teka.android.chamaaapp.features.feature_auth.data.remote.request.LoginRequest
import teka.android.chamaaapp.features.feature_auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}