package teka.android.chamaaapp.features.feature_auth.presentation.auth_dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import teka.android.chamaaapp.R
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.NormalShapes
import teka.android.chamaaapp.ui.theme.YellowMain
import teka.android.chamaaapp.ui.theme.quicksand
import kotlin.system.exitProcess


@Composable
fun AuthDashboardScreen(
    navigator: NavHostController
) {
    Dialog(
        onDismissRequest = {
            exitProcess(0)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.happy_lady),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 24.dp),
                        color = GreenEnd,
                        fontSize = 24.sp,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = GreenEnd)) {
                                append("Building")
                            }
                            withStyle(style = SpanStyle(color = YellowMain)) {
                                append(" Wealth")
                            }
                            append(" TOGETHER")
                        },
                        fontFamily = quicksand,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            navigator.navigate(Screen.LoginScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        shape = RoundedCornerShape(25.dp)

                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "Log In",
                            color = MainWhiteColor,
                            fontFamily = quicksand
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedButton(
                        onClick = {
                            navigator.navigate(Screen.RegisterScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Green,
                            containerColor = MainWhiteColor,
                        ),
                        border = BorderStroke(1.dp, Green),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "Sign Up",
                            fontFamily = quicksand
                        )
                    }
                    Spacer(modifier = Modifier.height(42.dp))
                }
            }
        }
    }
}