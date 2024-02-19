package teka.android.chamaaapp.features.feature_auth.domain.use_case.validation_use_cases

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}