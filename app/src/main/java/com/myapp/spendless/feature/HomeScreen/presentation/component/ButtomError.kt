package com.myapp.spendless.feature.HomeScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.Error

@Composable
fun ButtomError(message: String) {

    Box(
        modifier = Modifier
            .fillMaxSize().padding(vertical = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Error)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                color = Color.White,
                fontSize = 12.sp
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ButtomErrorPreview() {
    ButtomError("Pin does not matched. Try again.")
}
