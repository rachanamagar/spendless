package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.myapp.spendless.ui.theme.PrimaryFixed

@Composable
fun TransactionLayout(){

    Column(modifier = Modifier
        .height(72.dp)
        .background(PrimaryFixed, shape = RoundedCornerShape(16.dp))
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Groceries",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )

            Text(text = "$60.25",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )
        }
        Row( modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Largest Transaction",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                )

                Text(text = "Jan 27, 2025",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                )
            }
    }

}

@Preview(showBackground = true)
@Composable
fun TransactionPreviewPreview() {
    TransactionLayout()
}
