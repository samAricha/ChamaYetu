package teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases

import teka.android.chamaaapp.features.feature_auth.util.isPasswordValid


class ValidatePasswordUseCase : BaseUseCase<String, ValidationResultModel> {
    override fun execute(input: String): ValidationResultModel {
        if (input.length < 8) {
            return ValidationResultModel(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters",
            )
        }

        if (!isPasswordValid(input)) {
            return ValidationResultModel(
                successful = false,
                errorMessage =  "The password needs to contain at least one letter and digit",
            )
        }
        return ValidationResultModel(
            successful = true
        )
    }
}