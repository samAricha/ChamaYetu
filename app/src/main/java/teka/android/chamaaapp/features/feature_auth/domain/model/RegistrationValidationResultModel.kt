package teka.android.chamaaapp.features.feature_auth.domain.model

import teka.android.chamaaapp.util.ResultResource


data class RegistrationValidationResultModel(
    var phoneError: String? = null,
    var emailError: String? = null,
    var passwordError: String? = null,
    var passwordConfirmationError: String? = null,
    var result: ResultResource<Unit>? = null
)
