package teka.android.chamaaapp.features.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Geolocation(
    val lat: String,
    val long: String
)