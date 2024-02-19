package teka.android.chamaaapp.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.ui.theme.MensColor
import teka.android.chamaaapp.ui.theme.NormalShapes
import teka.android.chamaaapp.ui.theme.quicksand


@Composable
fun MemberListItemCard(
    memberEntity: MemberEntity,
    onItemClick: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable {
                onItemClick.invoke()
            },
        shape = NormalShapes.medium,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MensColor)
                .padding(6.dp)
        ){
                val icon = if (memberEntity.isBackedUp) {
                    painterResource(teka.android.chamaaapp.R.drawable.checkmark) // "Backed Up" icon
                } else {
                    painterResource(teka.android.chamaaapp.R.drawable.cloud_not_done) // "Not Backed Up" icon
                }


                Column() {
                    Text(
                        text = "${memberEntity.firstName + memberEntity.lastName}",
                        fontWeight = FontWeight.Light,
                        fontFamily = quicksand
                    )
                    Text(text = "Phone: ${memberEntity.contactInformation}",
                        fontWeight = FontWeight.Light,
                        fontFamily = quicksand
                    )
                }
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Image(
                            painter = icon,
                            modifier = Modifier.size(24.dp),
                            contentDescription = if (memberEntity.isBackedUp) "Backed Up" else "Not Backed Up"
                        )
                    }
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                    ) {
                        Text(text = memberEntity.dateJoined,
                            fontWeight = FontWeight.ExtraLight,
                            fontFamily = quicksand,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }


                }


            }


    }
}