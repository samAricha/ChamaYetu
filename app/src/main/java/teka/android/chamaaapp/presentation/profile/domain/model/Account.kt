package teka.android.chamaaapp.presentation.profile.domain.model

data class Account(
    val title: String,
    val content: String,
    val onClick: () -> Unit
)