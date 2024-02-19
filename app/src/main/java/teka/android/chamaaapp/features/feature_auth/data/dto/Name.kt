package teka.android.chamaaapp.features.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val firstname: String,
    val lastname: String
)