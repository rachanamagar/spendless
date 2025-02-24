package com.myapp.spendless.presentation.Registration

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.spendless.R
import com.myapp.spendless.presentation.component.ButtomError
import com.myapp.spendless.presentation.viewmodels.UserViewmodel
import com.myapp.spendless.ui.theme.BackgroundBlack
import com.myapp.spendless.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    viewmodel: UserViewmodel = hiltViewModel(),
    onNextClicked: () -> Unit,
    onLogin: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val state by viewmodel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()


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
            text = "Welcome to SpendLess!\n" +
                    "How can we address you?",
            fontSize = 28.sp,
            lineHeight = 36.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Create unique username",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.fig_tree_loight)
            )
        )

        OutlinedTextField(
            value = state.user.name,
            onValueChange = { newText ->
                viewmodel.getUsername(newText)
                coroutineScope.launch  {
                    viewmodel.isExistingUser(newText)
                }
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
                .padding(20.dp)
                .clip(RoundedCornerShape(16.dp))
                .focusRequester(focusRequester)

        )

        Button(
            onClick = {
                onNextClicked()
                Log.d("TAG", state.user.name)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.user.name.isNotEmpty()) Primary else BackgroundBlack.copy(
                    alpha = 0.2f
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Next",
                color = if (state.user.name.isNotEmpty()) Color.White else Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )

            Icon(
                painter = painterResource(R.drawable.forward_arrow),
                tint = Color.Gray,
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Already have an account?",
            color = Primary,
            fontFamily = FontFamily(Font(R.font.fig_tee_bold)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onLogin() }
        )

        if (state.isExistingUser){
            ButtomError("The username has been taken.")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val viewmodel: UserViewmodel = hiltViewModel()
    WelcomeScreen(viewmodel, {}) {}
}