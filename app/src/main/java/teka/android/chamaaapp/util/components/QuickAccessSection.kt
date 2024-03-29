package teka.android.chamaaapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.PersonAddAlt1
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import teka.android.chamaaapp.domain.data.Finance
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.BlueStart
import teka.android.chamaaapp.ui.theme.GrayColor
import teka.android.chamaaapp.ui.theme.GreenBall
import teka.android.chamaaapp.ui.theme.GreenStart
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.MensColor
import teka.android.chamaaapp.ui.theme.OrangeStart
import teka.android.chamaaapp.ui.theme.lightGray
import teka.android.chamaaapp.ui.theme.quicksand

val financeList = listOf(
    Finance(
        icon = Icons.Rounded.MonetizationOn,
        name = "Add\nContribution",
        background = GreenStart,
        route = Screen.AddContributionScreen.route
    ),
    Finance(
        icon = Icons.Rounded.PersonAddAlt1,
        name = "Add\nMember",
        background = OrangeStart,
        route = Screen.AddMemberScreen.route
    ),

//    Finance(
//        icon = Icons.Rounded.Wallet,
//        name = "Chamaa\nWallet",
//        background = BlueStart,
//        route = Screen.WalletScreen.route
//
//    ),

//    Finance(
//        icon = Icons.Rounded.GroupAdd,
//        name = "Create\nChamaa",
//        background = PurpleStart
//    ),


)

@Composable
fun QuickAccessSection(
    navController: NavController
) {
    Column {
        Text(
            text = "Quick Access",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            fontFamily = quicksand
        )

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 5.dp)
        ) {

            FlowRow(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp),
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                financeList.forEach {
                    QuickAccessItem(
                        finance = it,
                        navController = navController,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickAccessItem(
    finance: Finance,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(end = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MensColor)
                .fillMaxWidth()
                .clickable {}
                .padding(13.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Card(
                onClick = { navController.navigate(finance.route) },
                colors = CardDefaults.cardColors(
                    containerColor = MensColor,
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(finance.background)
                            .padding(6.dp)

                    ) {
                        Icon(
                            imageVector = finance.icon,
                            contentDescription = finance.name,
                            tint = Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = finance.name,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        fontFamily = quicksand
                    )
                }
            }


        }
    }
}













