package com.myapp.spendless.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.home.model.Transaction
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryText
import com.myapp.spendless.ui.theme.SurfaceBackground
import java.util.UUID

@Composable
fun BottomRow(list: List<Transaction>, onShowAll: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                PrimaryContainer.copy(alpha = 0.1f),
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    SurfaceBackground,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (list.isEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    "No transactions to show",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.last_transaction),
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            modifier = Modifier
                        )
                        Text(text = "Show all",
                            fontSize = 14.sp,
                            color = PrimaryText,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            modifier = Modifier.clickable { onShowAll() })
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TransactionList(list)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonRowPreview() {
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
    BottomRow(
        list = cat
    ) {}
}
