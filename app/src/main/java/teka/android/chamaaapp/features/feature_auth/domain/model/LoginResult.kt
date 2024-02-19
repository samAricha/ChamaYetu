package teka.android.chamaaapp.features.feature_auth.domain.model

import teka.android.chamaaapp.util.ResultResource


data class LoginResult(
    var passwordError: String? = null,
    var usernameError: String? = null,
    var result: ResultResource<Unit>? = null
)
