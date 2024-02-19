package teka.android.chamaaapp.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import teka.android.chamaaapp.R
import teka.android.chamaaapp.data.local.room.entities.ContributionWithMemberName
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.NormalShapes
import teka.android.chamaaapp.ui.theme.WomensColor
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.contributionLevelImageName

@Composable
fun ContributionProgressCard(
    contributions: List<ContributionWithMemberName>,
    members: List<MemberEntity>
    ) {
    Card(
        modifier = Modifier
            .clip(shape = NormalShapes.extraLarge),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            Modifier
                .background(WomensColor)
                .padding(12.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ContributionProgress(
                    mainColor = GreenEnd,
                    percentage = calculatePercentage(
                        members.size.toFloat(),
                        contributions.size.toFloat()
                    ),
                    counterColor = Green,
                )

                Column(

                ) {
//                    Text(
////                        text = taskCompleteMessage(tasks),
//                        text = taskCompleteMessage(emptyList(),8),
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Medium,
//                            fontSize = 18.sp,
//                            fontFamily = quicksand
//                        ),
//                        lineHeight = 16.sp
//                    )

                    Card(
                        modifier = Modifier
                            .clip(shape = NormalShapes.extraLarge)
                            .size(76.dp)
                            .align(Alignment.CenterHorizontally),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ){
                        val imgName = contributionLevelImageName(
                            calculatePercentage(
                                members.size.toFloat(),
                                contributions.size.toFloat()
                            )
                        )


                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(getImageResourceId(imgName)),
                            contentDescription = "visual display of contributions percentage",
                        )
                    }


                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Contributed : ${contributions.size} members",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = quicksand
                        ),
                    )
                    Text(
                        text = "Total : ${members.size} members ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = quicksand
                        ),
                    )
                }
            }
        }
    }

}


fun calculatePercentage(total: Float, contributed: Float): Float {
    return if (contributed != 0f) {
        (contributed / total) * 100
    } else {
        // Handle the case when contributed is zero (e.g., return a default value or handle it as needed)
        0f
    }
}

// Function to get the resource ID based on the image name
fun getImageResourceId(imageName: String): Int {
    // Map your image names to resource IDs
    val imageResourceMap = mapOf(
        "contrib1" to R.drawable.contrib1,
        "contrib2" to R.drawable.contrib2,
        "contrib3" to R.drawable.contrib3,
        "contrib4" to R.drawable.contrib4,
        "defaultImage" to R.drawable.contrib4
    )

    return imageResourceMap[imageName] ?: R.drawable.contrib4
}
