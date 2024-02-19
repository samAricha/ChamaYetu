package teka.android.chamaaapp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChamaDTO(
    @SerialName("id")
    val chamaId: Long,
    @SerialName("chama_name")
    val chamaName: String,
    @SerialName("chama_description")
    val chamaDescription: String,
    @SerialName("date_formed")
    val dateFormed: String
)
