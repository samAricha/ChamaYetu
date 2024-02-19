package teka.android.chamaaapp.features.feature_auth.domain.repository

import teka.android.chamaaapp.features.feature_auth.data.remote.request.LoginRequest
import teka.android.chamaaapp.util.ResultResource

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): ResultResource<Unit>
    suspend fun autoLogin(): ResultResource<Unit>
    suspend fun logout(): ResultResource<Unit>
}
