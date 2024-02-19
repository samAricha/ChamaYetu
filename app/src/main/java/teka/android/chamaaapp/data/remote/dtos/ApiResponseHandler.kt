package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseHandler<T>(
    val isSuccessful: Boolean,
    val message: String?,
    val data: T?
)
