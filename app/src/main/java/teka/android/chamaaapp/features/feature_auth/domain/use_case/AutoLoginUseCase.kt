package teka.android.chamaaapp.features.feature_auth.domain.use_case

import teka.android.chamaaapp.features.feature_auth.domain.repository.LoginRepository
import teka.android.chamaaapp.util.ResultResource

class AutoLoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): ResultResource<Unit> {
        return loginRepository.autoLogin()
    }
}