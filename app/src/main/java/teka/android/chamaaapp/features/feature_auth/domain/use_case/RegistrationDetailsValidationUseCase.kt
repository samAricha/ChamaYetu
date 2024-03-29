package teka.android.chamaaapp.features.feature_auth.domain.use_case

import teka.android.chamaaapp.features.feature_auth.domain.model.RegistrationValidationResultModel
import teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases.ValidateEmailUseCase
import teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases.ValidatePasswordUseCase
import teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases.ValidatePhoneNumberUseCase
import teka.android.chamaaapp.util.ResultResource

class RegistrationDetailsValidationUseCase() {
    suspend operator fun invoke(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        rememberMe: Boolean
    ): RegistrationValidationResultModel {
        val validateEmailUseCase = ValidateEmailUseCase()
        val validatePasswordUseCase = ValidatePasswordUseCase()
        val validatePhoneNumberUseCase = ValidatePhoneNumberUseCase()


        val emailResult = validateEmailUseCase.execute(email)
        val passwordResult = validatePasswordUseCase.execute(password)
        val passwordConfirmationResult = validatePasswordUseCase.execute(passwordConfirmation)
        val phoneNumberResult = validatePhoneNumberUseCase.execute(phone)

        val phoneError = if (!phoneNumberResult.successful) phoneNumberResult.errorMessage else null
        val emailError = if (!emailResult.successful) emailResult.errorMessage else null
        val passwordError = if (!passwordResult.successful) passwordResult.errorMessage else null
        val passwordConfirmationError = if (!passwordConfirmationResult.successful) passwordConfirmationResult.errorMessage else null

        if (phoneError != null || emailError != null || passwordError != null || passwordConfirmationError != null){
            return RegistrationValidationResultModel(
                phoneError = phoneError,
                emailError = emailError,
                passwordError = passwordError,
                passwordConfirmationError = passwordConfirmationError,
            )
        }

        // Simulate a successful login result without making an actual repository call
        return RegistrationValidationResultModel(result = ResultResource.Success(Unit))
    }
}