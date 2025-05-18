package com.myapp.spendless.feature.auth.presentation.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.PrimaryFixed

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
fun PinDeletePreview() {
    PinDelete { }
}