package teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases

import teka.android.chamaaapp.features.feature_auth.util.isNumber


class ValidatePhoneNumberUseCase : BaseUseCase<String, ValidationResultModel> {

    override fun execute(input: String): ValidationResultModel {
        if (input.isBlank()) {
            return ValidationResultModel(
                successful = false,
                errorMessage = "The phone can't be blank",
            )
        }

        if (!isNumber(input)) {
            return ValidationResultModel(
                successful = false,
                errorMessage = "The phone number should be content just digit",
            )
        }

        if (input.length != 10) {
            return ValidationResultModel(
                successful = false,
                errorMessage = "The phone number should be 10 digits",
            )
        }

        return ValidationResultModel(
            successful = true,
            errorMessage = null
        )
    }
}