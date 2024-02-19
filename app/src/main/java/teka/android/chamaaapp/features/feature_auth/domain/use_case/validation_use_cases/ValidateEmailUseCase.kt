package teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases

import teka.android.chamaaapp.features.feature_auth.util.isEmailValid


class ValidateEmailUseCase: BaseUseCase<String, ValidationResultModel> {
    override fun execute(input: String): ValidationResultModel {
        if (input.isBlank()) {
            return ValidationResultModel(
                successful = false,
                errorMessage =  "The email can't be blank"
            )
        }
        if (!isEmailValid(input)) {
            return ValidationResultModel(
                successful = false,
                errorMessage =  "That's not a valid email"
            )
        }
        return ValidationResultModel(
            successful = true,
            errorMessage = null
        )
    }
}