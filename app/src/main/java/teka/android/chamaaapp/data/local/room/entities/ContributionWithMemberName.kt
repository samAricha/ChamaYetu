package teka.android.chamaaapp.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ContributionWithMemberName(
    @Embedded val contribution: ContributionEntity,
    @ColumnInfo(name = "memberFirstName") val firstName: String,
    @ColumnInfo(name = "memberLastName") val lastName: String
)