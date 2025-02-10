package com.myapp.spendless.presentation.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.BackgroundBlack
import com.myapp.spendless.ui.theme.Primary

@Composable
fun LoginScreen(navController: NavController, onLoginClicked:() -> Unit) {

    var text by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(R.drawable.logo),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = " Welcome Back !",
            fontSize = 28.sp,
            lineHeight = 36.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Enter you login details",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.fig_tree_loight)
            )
        )

        Spacer(modifier = Modifier.height(36.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText

            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = BackgroundBlack.copy(alpha = 0.1f),
                focusedContainerColor = BackgroundBlack.copy(alpha = 0.1f),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.fig_tree_regular))
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            placeholder = {
                Text(
                    text = "username",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    fontFamily = FontFamily(
                        Font(
                            R.font.fig_tree_regular
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .focusRequester(focusRequester)

        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier .clickable { navController.navigate("PinLoginScreen/$text") }) {
           Text("Pin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onLoginClicked()
                Log.d("TAG", text)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =  Primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp)

        ) {
            Text(
                text = "Log in",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "New to SpendLess ?",
            color = Primary,
            fontFamily = FontFamily(Font(R.font.fig_tee_bold)),
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController){  }
}

