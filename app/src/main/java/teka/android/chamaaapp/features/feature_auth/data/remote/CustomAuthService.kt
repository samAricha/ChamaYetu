package teka.android.chamaaapp.features.feature_auth.data.remote

import retrofit2.Response
import teka.android.chamaaapp.features.feature_auth.util.models.AuthResponse
import teka.android.chamaaapp.features.feature_auth.util.models.LoginRequestBody
import teka.android.chamaaapp.features.feature_auth.util.models.PersonInfoRequest
import teka.android.chamaaapp.features.feature_auth.util.models.RegisteredUser
import retrofit2.http.Body
import retrofit2.http.POST
import teka.android.chamaaapp.features.feature_auth.util.models.LoginAuthResponse
import teka.android.chamaaapp.features.feature_auth.util.models.RegisterRequestBody

interface CustomAuthService {

    @POST("api/auth/register")
    suspend fun registration(
        @Body registerRequest: RegisterRequestBody
    ): Response<AuthResponse>

    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequestBody
    ): Response<LoginAuthResponse>

    @POST("/api/auth/me")
    suspend fun getMeInfo(
        @Body personInfoRequest: PersonInfoRequest
    ): RegisteredUser
}