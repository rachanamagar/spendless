package com.myapp.spendless.presentation.LoginScreen

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.myapp.spendless.R
import com.myapp.spendless.presentation.component.ButtomError
import com.myapp.spendless.presentation.Registration.PinDelete
import com.myapp.spendless.presentation.Registration.PinNumber
import com.myapp.spendless.presentation.viewmodels.UserViewmodel
import com.myapp.spendless.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun PinLoginScreen(name: String, navController: NavController) {

    var pinCode by remember { mutableStateOf<String>("") }
    var isPinComplete by remember { mutableStateOf(false) }

    val viewmodel: UserViewmodel = hiltViewModel()

    var userValid by remember { mutableStateOf<Boolean?>(null) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
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

        Text(
            text = "Hello, $name !",
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Enter your PIN",
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
            }

            Spacer(modifier = Modifier.height(40.dp))

            val list1 = (1..9).map { it.toString() }

            Column {
                list1.chunked(3).forEach { numbers ->
                    Row {
                        numbers.forEach { number ->
                            PinNumber(number = number) { pinCode += number }
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
                PinNumber("0") { pinCode += "0" }
                PinDelete() {
                    if (pinCode.isNotEmpty()) {
                        pinCode = pinCode.dropLast(1)
                    }
                }
            }
        }
    }
    if (pinCode.length == pinLength) {
        Log.d("TAG", pinCode.length.toString())
        isPinComplete = true

        Log.d("TAG", isPinComplete.toString())
    }

    if (isPinComplete){
       viewmodel.validateUser(name, pinCode){
           if (it){
               userValid = true
               viewmodel.saveUserName(name)
               navController.navigate("HomeScreen")
           }
           else{
               userValid = false
           }
        }
        if (userValid== false){
            ButtomError("Wrong Pin")
            LaunchedEffect(Unit) {
                delay(1000)
                pinCode = ""
                isPinComplete = false
            }
        }
    }
}