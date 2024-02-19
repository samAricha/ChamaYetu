package teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases

data class ValidationResultModel(
    val successful: Boolean,
    val errorMessage: String? = null
)