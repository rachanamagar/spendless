package com.myapp.spendless.presentation.setting.preference.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.presentation.setting.toExpensesUnit

@Composable
fun TotalAmountBox(totalDisplayAmount: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color.White, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = totalDisplayAmount.toExpensesUnit(Color.Black),
                fontFamily =
                FontFamily(Font(R.font.fig_tree_medium)),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "spend this month",
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                fontSize = 12.sp, color = Color.Gray
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreferenceScreenPreview() {
    TotalAmountBox("120" )
}