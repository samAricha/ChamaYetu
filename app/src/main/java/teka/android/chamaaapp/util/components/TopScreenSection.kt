package teka.android.chamaaapp.util.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.chamaaapp.ui.theme.quicksand

@Preview
@Composable
fun TopScreenSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = "Chamaa",
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold
            )

        }

//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(15.dp))
//                .background(MaterialTheme.colorScheme.secondaryContainer)
//                .clickable {}
//                .padding(6.dp),
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Search,
//                contentDescription = "Search",
//                tint = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//        }

    }
}














