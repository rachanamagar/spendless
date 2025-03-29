package com.myapp.spendless.feature.HomeScreen.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.toExpensesUnit
import com.myapp.spendless.ui.theme.SecondaryFixed

@SuppressLint("DefaultLocale")
@Composable
fun PreviousWeekTransaction(amount: String){

    Box(
        modifier = Modifier
            .height(72.dp)
            .width(132.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SecondaryFixed, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = amount,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )
            Text(
                "Previous week",
                fontSize = 12.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviousTransactionPreview(){
    PreviousWeekTransaction("$0.0")
}