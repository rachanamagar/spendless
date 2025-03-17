package com.myapp.spendless.feature.HomeScreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import com.myapp.spendless.feature.HomeScreen.presentation.component.TransactionListItem
import java.util.Locale
import java.util.UUID


@Composable
fun TransactionList(list: List<Transaction>) {

    val listToShow = list.groupBy { it.date }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            listToShow.forEach { (date, transaction) ->
                item {
                    Text(
                        text = formatDateLabel(date).uppercase(locale = Locale.US),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }

                itemsIndexed(transaction) {_, eachTransaction ->
                    TransactionListItem(eachTransaction)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListRowPreview() {

    val cat = listOf(
        Transaction(
            id = 1,
            title = "Decoration",
            amount = "2000.0".toDouble(),
            note = "Home decoration expenses",
            icon = R.drawable.home,
            category = "Home",
            date = 2,
            userId = UUID.randomUUID()
        ), Transaction(
            id = 1,
            title = "Transportation",
            amount = "2000.0".toDouble(),
            note = "Home decoration expenses",
            icon = R.drawable.transport,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        ),
        Transaction(
            id = 1,
            title = "Food",
            amount = "2000.0".toDouble(),
            note = "Groceries",
            icon = R.drawable.food,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        ), Transaction(
            id = 1,
            title = "Education",
            amount = "2000.0".toDouble(),
            note = "Course Book Purchase",
            icon = R.drawable.education,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        )
    )
    TransactionList(cat)

}
