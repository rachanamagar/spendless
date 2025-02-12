package com.myapp.spendless.presentation.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.model.User
import com.myapp.spendless.presentation.viewmodels.UserViewmodel
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryFixed

@Composable
fun PinScreen(navController: NavController, viewmodel: UserViewmodel) {

    val state by viewmodel.state.collectAsStateWithLifecycle()

    var pinCodeList by remember { mutableStateOf<List<String>>(emptyList()) }
    var confirmPinCode by remember { mutableStateOf<String>("") }
    var isPinComplete by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val pinCode = pinCodeList.joinToString("")
    val pinLength = 5

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        Icon(
            painter = painterResource(R.drawable.logo),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = state.user.name)
        Log.d("TAG", "the name is ${state.user.name}")
        Text(
            text = if (pinCode.length < pinLength) "Create PIN" else "Repeat your PIN",
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (pinCode.length < pinLength) "Use PIN to login to your account " else "Enter your PIN again",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.fig_tree_loight)
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isPinComplete) {
                    repeat(pinLength) { index ->
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = if (index < pinCode.length) Primary else Color.LightGray,
                                    shape = CircleShape
                                )
                        )
                    }
                } else {
                    repeat(pinLength) { index ->
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = if (index < confirmPinCode.length) Primary else Color.LightGray,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            val list1 = (1..9).map { it.toString() }

            Column {
                list1.chunked(3).forEach { numbers ->
                    Row {
                        if (!isPinComplete) {
                            numbers.forEach { number ->
                                PinNumber(number = number) {
                                    pinCodeList = pinCodeList + number
                                }
                            }
                        } else {
                            numbers.forEach { number ->
                                PinNumber(number = number) { confirmPinCode += number }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isPinComplete) {
                    PinNumber("0") { pinCodeList = pinCodeList + "0" }
                    PinDelete() {
                        if (pinCode.isNotEmpty()) {
                            pinCodeList = pinCodeList.dropLast(1)
                        }
                    }
                } else {
                    PinNumber("0") { confirmPinCode += "0" }
                    PinDelete() {
                        if (confirmPinCode.isNotEmpty()) {
                            confirmPinCode = confirmPinCode.dropLast(1)
                        }
                    }
                }
            }
        }
    }
    if (pinCode.length == pinLength) {
        isPinComplete = true
    }

    if (confirmPinCode.length == pinLength) {
        if (pinCode != confirmPinCode) {
            Log.d("TAG", pinCode)
            Log.d("TAG", confirmPinCode)
            ButtomError("PINs don’t match. Try again")
        } else {
            viewmodel.getPin(pinCode)
            Log.d("TAG", pinCode)
            viewmodel.insertUser()
            navController.navigate("LoginScreen")
        }
    }
}


@Composable
fun PinNumber(number: String, onPinClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(color = PrimaryFixed)
            .size(90.dp),
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = number,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onPinClicked() }
        )
    }
}


@Composable
fun PinDelete(onDelete: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(color = PrimaryFixed.copy(alpha = 0.5f))
            .size(90.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.crossbg),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onDelete() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PinScreenPreview() {
    val navController = rememberNavController()
    val viewmodel: UserViewmodel = hiltViewModel()
    PinScreen(navController, viewmodel = viewmodel)
}

@Preview(showBackground = true)
@Composable
fun PinNumberPreview() {
    PinNumber("2") {}
}