package teka.android.chamaaapp.data.room_remote_sync.models

import kotlinx.serialization.Serializable

@Serializable
data class UploadDataResponse(
    val success: Boolean,
    val message: String?,
)
