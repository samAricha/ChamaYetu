package teka.android.chamaaapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.GreenStart
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.quicksand

@Composable
fun FeaturedBox(
    totalMembers : String,
    totalChamaaAccounts: String
){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10))
            .background(getGradient(GreenStart, GreenEnd))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 20.dp),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Table Banking\nPlatform",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Spacer(modifier = Modifier.size(0.05.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Banking our own way",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = quicksand
                )

                Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Members : $totalMembers",
                                color = MainWhiteColor,
                                fontSize = 14.sp,
                                )
                            Text(
                                text = "Accounts : $totalChamaaAccounts",
                                color = MainWhiteColor,
                                fontSize = 14.sp,
                                )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
//                        Column(
//                            Modifier
//                                .absolutePadding(0.dp, 0.dp, 0.dp, 0.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Button(
//                                modifier = Modifier
//                                    .clip(shape = RoundedCornerShape(20)),
//                                onClick = { /*TODO*/ },
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color.White
//                                )
//                            ) {
//                                Text(
//                                    text = "Record",
//                                    color = GreenStart,
//                                    fontSize = 18.sp,
//                                    fontFamily = quicksand,
//                                    fontWeight = FontWeight.Bold,
//                                    textAlign = TextAlign.Center,
//                                )
//                            }
//                        }
                    }

                }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    FeaturedBox(totalMembers = "6", totalChamaaAccounts = "2")
}