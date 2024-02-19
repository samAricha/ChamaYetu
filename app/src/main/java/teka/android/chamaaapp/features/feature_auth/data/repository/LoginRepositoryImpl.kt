package teka.android.chamaaapp.features.feature_auth.data.repository

import teka.android.chamaaapp.features.feature_auth.data.dto.UserResponseDto
import teka.android.chamaaapp.features.feature_auth.data.local.AuthPreferences
import teka.android.chamaaapp.features.feature_auth.data.remote.AuthApiService
import teka.android.chamaaapp.features.feature_auth.data.remote.request.LoginRequest
import teka.android.chamaaapp.features.feature_auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import teka.android.chamaaapp.util.ResultResource
import timber.log.Timber
import java.io.IOException

class LoginRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): ResultResource<Unit> {
        Timber.d("Login called")
        return try {
            val response = authApiService.loginUser(loginRequest)
            Timber.d("Login Token: ${response.token}")

//            getAllUsers(loginRequest.username)?.let { authPreferences.saveUserdata(it) }

            if (rememberMe) {
                authPreferences.saveAccessToken(response.token)
            }
            ResultResource.Success(Unit)
        } catch (e: IOException) {
            ResultResource.Error(message = "Could not reach the server, please check your internet connection!")
        } catch (e: HttpException) {
            ResultResource.Error(message = "An Unknown error occurred, please try again!")
        }
    }

    override suspend fun autoLogin(): ResultResource<Unit> {
        val accessToken = authPreferences.getAccessToken.first()
        Timber.d("Auto login access token: $accessToken")
        return if (accessToken != "") {
            ResultResource.Success(Unit)
        } else {
            ResultResource.Error("")
        }
    }

    override suspend fun logout(): ResultResource<Unit> {
        return try {
            authPreferences.clearAccessToken()
            val fetchedToken = authPreferences.getAccessToken.first()
            Timber.d("token: $fetchedToken")

            if (fetchedToken.isEmpty()) {
                ResultResource.Success(Unit)
            } else {
                ResultResource.Error("Unknown Error")
            }
        } catch (e: Exception) {
            return ResultResource.Error("Unknown error occurred")
        }
    }

    private suspend fun getAllUsers(name: String): UserResponseDto? {
        val response = authApiService.getAllUsers()
        return response.find { it.username == name }
    }
}