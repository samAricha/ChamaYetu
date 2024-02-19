package teka.android.chamaaapp.features.feature_auth.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import teka.android.chamaaapp.data.remote.networking.AuthRetrofitProvider
import teka.android.chamaaapp.features.feature_auth.data.remote.CustomAuthService
import teka.android.chamaaapp.features.feature_auth.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import teka.android.chamaaapp.features.feature_auth.util.models.AuthErrors
import teka.android.chamaaapp.features.feature_auth.util.models.LoginAuthResponse
import teka.android.chamaaapp.features.feature_auth.util.models.LoginAuthResponseError
import teka.android.chamaaapp.features.feature_auth.util.models.LoginRequestBody
import teka.android.chamaaapp.features.feature_auth.util.models.RegisterRequestBody
import timber.log.Timber

import javax.inject.Inject

sealed class RegistrationResult<out T> {
    data class Success<out T>(val data: T) : RegistrationResult<T>()
    data class Failure(val exception: Throwable, val errorMessage: String? = null) : RegistrationResult<Nothing>()
}

sealed class LoginResultModel {
    data class Success(val isLoggedIn: Boolean) : LoginResultModel()
    data class Failure(val exception: Throwable, val errorMessage: String? = null) : LoginResultModel()
}

class AuthManager @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val context: Context // Inject the Context

) {

    private val authService: CustomAuthService = AuthRetrofitProvider.createAuthService()

    suspend fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): RegistrationResult<Boolean> {
        return try {
            val response = authService.registration(
                RegisterRequestBody(
                    name = "MobileUserName",
                    phone = phone,
                    email = email,
                    password = password,
                    password_confirmation = passwordConfirmation
                )
            )
            Timber.d("FIRSTRESPONSE----> ${response}.")

            if (response.isSuccessful) {
                Timber.d("SUCCESSRESPONSE----> .")

                RegistrationResult.Success(true)
            } else {
                Timber.d("NOSUCCESSRESPONSE----> .")

                if (response.code() == 422) {
                    val json = Json { ignoreUnknownKeys = true }
                    // Handle validation errors
                    val errorResponseBody = response.errorBody()?.string()
                    val authErrors = json.decodeFromString<AuthErrors>(errorResponseBody ?: "")
                    val errorMessage = extractErrorMessage(authErrors)
                    RegistrationResult.Failure(Exception("Validation Error"), errorMessage)
                } else {
                    RegistrationResult.Success(false)
                }
            }
        } catch (e: Exception) {
            Timber.d("EXCEPTIONRESPONSE----> ${e.message}.")

            RegistrationResult.Failure(e)
        }
    }


    private suspend fun saveAuthToken(token: String) = withContext(Dispatchers.IO) {
        dataStoreRepository.saveToken(token)
    }

    suspend fun getAuthToken(): String {
        return dataStoreRepository.getAccessToken.first()
    }

    suspend fun clearAuthToken() {
        dataStoreRepository.saveToken("")
    }


    @SuppressLint("TimberArgCount")
    suspend fun login(
        username: String,
        password: String
    ): LoginResultModel {
        return try {
            val response = authService.login(LoginRequestBody(username, password))
            Timber.d("FIRSTRESPONSE----> ${response}.")

            if (response.isSuccessful) {
                val token = response.body()?.data?.access_token
                if (token != null) {
                    saveAuthToken(token)
                }
                val savedToken = dataStoreRepository.getAccessToken
                LoginResultModel.Success(true)

            } else {
                Timber.d("SECONDRESPONSE----> ${response.code()}.")

                when (response.code()) {
                    401 -> {
                        val json = Json { ignoreUnknownKeys = true }
                        Timber.d("INSIDE 401----> ${response.code()}.")

                        // Handle 401 Unauthorized
                        val errorResponse: ResponseBody? = response.errorBody()
                        var errorMessage = "Wrong Credentials"
                        if (errorResponse != null) {
                            val errorResponseBody = errorResponse.string()
                            Timber.d("INSIDE 401----> $errorResponseBody.")

                            try {
                                val json = Json { ignoreUnknownKeys = true }
                                val authResponse = json.decodeFromString<LoginAuthResponseError>(errorResponseBody)

                                errorMessage = authResponse.message ?: "Unknown error"
                                Timber.d("Error Message: $errorMessage")
                                return LoginResultModel.Failure(Exception(authResponse.status), errorMessage)

                            } catch (e: Exception) {
                                Timber.e(e, "Error parsing JSON response")
                            }
                        }
                        LoginResultModel.Failure(Exception("errorMessage"), errorMessage)
                    }
                    422 -> {
                        val json = Json { ignoreUnknownKeys = true }

                        val errorResponse = response.errorBody()?.string()
                        val authResponse = Json.decodeFromString<LoginAuthResponse>(errorResponse ?: "")
                        val errorMessage = authResponse.message ?: "Credentials do not match"
                        LoginResultModel.Failure(Exception(errorMessage), errorMessage)

                    }
                    else -> {
                        LoginResultModel.Failure(Exception("Login failed"))
                    }
                }
            }
        } catch (e: Exception) {
            Timber.tag("<<<<<AuthManager>>>>>").e("Log in was unsuccessful", e)
            LoginResultModel.Failure(Exception(e), "Credentials do not match")
            LoginResultModel.Failure(e)
        }
    }

}

private fun extractErrorMessage(authErrors: AuthErrors?): String? {
    return authErrors?.errors?.entries?.joinToString("\n") { (field, messages) ->
        "$field: ${messages.joinToString(", ")}"
    }
}
