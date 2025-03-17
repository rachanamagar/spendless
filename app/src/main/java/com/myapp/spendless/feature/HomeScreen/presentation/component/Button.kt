package com.myapp.spendless.feature.HomeScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.Primary

@Composable
fun AppButton(modifier: Modifier = Modifier, name: String) {

    Column(
        modifier = modifier
            .background(Primary, RoundedCornerShape(12.dp))
            .width(380.dp)
            .height(48.dp)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = name, color = Color.White,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
        )
    }

}


@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    AppButton(modifier = Modifier, "Save")
}