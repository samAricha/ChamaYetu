package teka.android.chamaaapp.features.feature_auth.presentation.forgot_password

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.primaryDark
import teka.android.chamaaapp.ui.theme.primaryGray

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen() {
    val context = LocalContext.current

    Scaffold(
//        backgroundColor = MainWhiteColor,
        topBar = {
            Column(Modifier.padding(16.dp)) {
                Text(text = "Forgot Password", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Please enter an email address that you had registered with, so that we can send you a password reset link",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) {
        ForgotPasswordScreenContent(
            onClickForgotPassword = {
                Toast.makeText(
                    context,
                    "This API does not provide an endpoint for sending password reset link, just login with the credentials provided in the README file",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ForgotPasswordScreenContent(
    onClickForgotPassword: () -> Unit,
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = primaryGray,
//                    textColor = primaryDark,
                    unfocusedLabelColor = primaryGray,
                    focusedLabelColor = primaryDark,
                    cursorColor = Color.Black
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onClickForgotPassword,
                shape = RoundedCornerShape(8)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Continue",
                    textAlign = TextAlign.Center,
                    color = MainWhiteColor
                )
            }
        }
    }
}