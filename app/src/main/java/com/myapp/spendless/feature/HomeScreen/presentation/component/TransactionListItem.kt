package com.myapp.spendless.feature.HomeScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import com.myapp.spendless.feature.Setting.toExpensesUnit
import com.myapp.spendless.feature.Setting.toIncomeUnit
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.ui.theme.SecondaryFixed
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun TransactionListItem(transaction: Transaction) {

    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (!isExpanded) Color.Transparent else Color.White,
                RoundedCornerShape(16.dp)
            )
            .clickable { isExpanded = !isExpanded }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if(transaction.category == "Income") SecondaryContainer.copy(alpha = 0.2f) else PrimaryFixed, RoundedCornerShape(12.dp))
                    .width(44.dp)
                    .height(44.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(transaction.icon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                if (isExpanded){
                    Icon(
                        painter = painterResource(R.drawable.descriptiveicon),
                        tint = if(transaction.category == "Income") SecondaryFixed else Primary,
                        contentDescription = "dragIcon",
                        modifier = Modifier.size(20.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color.White, shape = RoundedCornerShape(6.dp))
                            .padding(4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(4.dp)
            ) {
                Text(text = transaction.title, fontSize = 16.sp,  fontFamily = FontFamily(Font(R.font.fig_tree_medium)))
                Text(text = transaction.category, fontSize = 12.sp, color = Color.Gray, fontFamily = FontFamily(Font(R.font.fig_tree_medium)))
                Spacer(modifier = Modifier.height(4.dp))
                if (isExpanded) {
                    Text(
                        text = transaction.note,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            val amountText = if (transaction.category == "Income") {
                transaction.amount.toString().toIncomeUnit()
            } else {
                transaction.amount.toString().toExpensesUnit(Color.Black)
            }
            Text(
                text = amountText,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                modifier = Modifier
                    .padding(4.dp)
                //.weight(2f)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    val cat = Transaction(
        id = 1,
        title = "Decoration",
        amount = "2000.0".toDouble(),
        note = "Home decoration expenses",
        icon = R.drawable.home,
        category = "Home",
        date = 1,
        userId = UUID.randomUUID()
    )
    TransactionListItem(cat)
}

fun List<Transaction>.groupByDate(): Map<String, List<Transaction>> {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return this.groupBy { transaction ->
        val transactionDate = Instant.ofEpochMilli(transaction.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        when (transactionDate) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> transactionDate.format(dateFormatter)
        }
    }
}
