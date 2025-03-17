package com.myapp.spendless.feature.Setting.preference.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.SegmentedButton
import com.myapp.spendless.feature.Setting.preference.model.AmountFormat

@Composable
fun ExpenseAmountFormat(onClick: (AmountFormat) -> Unit) {
    var selectedIndexForFormat by remember { mutableIntStateOf(0) }

    Column {
        Text(
            text = "Expense format",
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        SegmentedButton(
            list = listOf("-$10", "($10)"),
            selectedIndex = selectedIndexForFormat,
            {
                selectedIndexForFormat = it
            },
            onItemClicked = { index ->
                when (index) {
                    0 -> onClick(AmountFormat.WithoutBrackets)
                    1 -> onClick(AmountFormat.WithBrackets)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseFormatPreview() {
    ExpenseAmountFormat(){

    }
}