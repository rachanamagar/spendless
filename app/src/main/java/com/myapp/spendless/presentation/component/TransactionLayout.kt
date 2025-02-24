package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.presentation.setting.toDollar
import com.myapp.spendless.ui.theme.PrimaryFixed
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun TransactionLayout(transaction: Transaction) {

    Column(
        modifier = Modifier
            .height(72.dp)
            .background(PrimaryFixed, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transaction.title,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )

            Text(
                text = transaction.amount.toString().toDollar(),
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Largest Transaction",
                fontSize = 10.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )

            Text(
                text = transaction.date.toDateFormat(),
                fontSize = 10.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            )
        }
    }
}

fun Long.toDateFormat(): String{
    val date = Date(this)
    val format =SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return format.format(date)
}



@Preview(showBackground = true)
@Composable
fun TransactionPreviewPreview() {
    TransactionLayout(
        Transaction(
            id = 9197,
            title = "habitasse",
            amount = 2.3,
            note = "verterem",
            category = "tractatos",
            icon = 7256,
            date = 1,
            userId = UUID.randomUUID()
        )
    )
}
