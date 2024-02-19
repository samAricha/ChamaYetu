package teka.android.chamaaapp.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.chamaaapp.R
import teka.android.chamaaapp.domain.data.Card
import teka.android.chamaaapp.ui.theme.BlueEnd
import teka.android.chamaaapp.ui.theme.BlueStart
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.GreenStart
import teka.android.chamaaapp.ui.theme.OrangeEnd
import teka.android.chamaaapp.ui.theme.OrangeStart
import teka.android.chamaaapp.ui.theme.PurpleEnd
import teka.android.chamaaapp.ui.theme.PurpleStart

val cards = listOf(

    Card(
        cardType = "VISA",
        cardNumber = "3664 7865 3786 3976",
        cardName = "Business",
        balance = 46.467,
        color = getGradient(PurpleStart, PurpleEnd),
    ),

    Card(
        cardType = "MASTER CARD",
        cardNumber = "234 7583 7899 2223",
        cardName = "Savings",
        balance = 6.467,
        color = getGradient(BlueStart, BlueEnd),
    ),

    Card(
        cardType = "VISA",
        cardNumber = "0078 3467 3446 7899",
        cardName = "School",
        balance = 3.467,
        color = getGradient(OrangeStart, OrangeEnd),
    ),

    Card(
        cardType = "MASTER CARD",
        cardNumber = "3567 7865 3786 3976",
        cardName = "Trips",
        balance = 26.47,
        color = getGradient(GreenStart, GreenEnd),
    ),
)



@Preview
@Composable
fun CardsSection() {
    LazyRow {
        items(cards.size) { index ->
            CardItem(index)
        }
    }
}

@Composable
fun CardItem(
    index: Int
) {
    val card = cards[index]
    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    var image = painterResource(id = R.drawable.ic_visa)
    if (card.cardType == "MASTER CARD") {
        image = painterResource(id = R.drawable.ic_mastercard)
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(card.color)
                .width(250.dp)
                .height(160.dp)
                .clickable {}
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = image,
                contentDescription = card.cardName,
                modifier = Modifier.width(60.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "ksh ${card.balance}",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = card.cardNumber,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}





























