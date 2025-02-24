package com.myapp.spendless.presentation.component.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.SurfaceBackground

@Composable
fun HighestTransactionSection(popular: String?){

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .height(70.dp)
            .fillMaxWidth()
            .background(PrimaryFixed.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .background(SurfaceBackground, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(26.dp),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                popular.let {
                    if (it != null) {
                        Text(
                            text = it,
                            fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
                Text(
                    "Most popular Category",
                    fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}