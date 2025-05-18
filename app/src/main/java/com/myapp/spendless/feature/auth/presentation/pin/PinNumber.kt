package com.myapp.spendless.feature.auth.presentation.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.ui.theme.PrimaryFixed

@Composable
fun PinNumber(number: String, onPinClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(color = PrimaryFixed)
            .size(90.dp),
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

@Preview(showBackground = true)
@Composable
fun PinNumberPreview() {
    PinNumber("2") {}
}