package com.myapp.spendless.presentation.component.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.presentation.component.AppButton
import com.myapp.spendless.presentation.component.TransactionState
import com.myapp.spendless.presentation.component.formatAmount
import com.myapp.spendless.presentation.component.formatAmountToFormatUnit
import com.myapp.spendless.presentation.component.formatDecimalCommaFormat
import com.myapp.spendless.presentation.component.formatThousandSpaceFormat
import com.myapp.spendless.presentation.component.toExpensesUnit
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(state: TransactionState, onBackPressed: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val navController = rememberNavController()

    var text by remember { mutableStateOf(formatAmount(state.totalAmount))}
    var selectedIndexForFormat by remember { mutableIntStateOf(0) }
    var selectedIndexForDecimal by remember { mutableIntStateOf(0) }
    var selectedIndexForThousands by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("$ US Dollar (USD)") }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { onBackPressed() })
                },
                title = {
                    Text(
                        text = "Preferences",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
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
                        text = text.toExpensesUnit(Color.Black),
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
            Spacer(modifier = Modifier.height(16.dp))
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

                   text = when (index) {
                        0 -> formatAmount(state.totalAmount)
                        1 -> formatAmountToFormatUnit(state.totalAmount)
                       else -> { text}
                   }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Currency",
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            ListOfCurrency(
                selectedCurrency = selectedCurrency,
                { selectedCurrency = it },
                expanded,
                { expanded = !expanded },
                toggledSelection = { expanded = !expanded }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Decimal seperator",
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            SegmentedButton(
                list = listOf("1.00", "1,00"),
                selectedIndex = selectedIndexForDecimal,
                onSelectedIndex = { selectedIndexForDecimal = it },
                onItemClicked = { indexDecimal ->
                    text = when (indexDecimal) {
                        0 -> formatAmount(state.totalAmount)
                        1 -> formatDecimalCommaFormat(state.totalAmount)
                        else -> text
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Thousands seperator",
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            SegmentedButton(
                list = listOf("1,000", "1.000", "1 000"),
                selectedIndex = selectedIndexForThousands,
                { selectedIndexForThousands = it }) { indexThousands ->
                text = when (indexThousands) {
                    0 -> formatAmount(state.totalAmount)
                    1 -> formatDecimalCommaFormat(state.totalAmount)
                    2 -> formatThousandSpaceFormat(state.totalAmount)
                    else -> text
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                modifier = Modifier
                    .clickable {
                        viewModel.changeFormat(text)
                        navController.popBackStack()
                               Log.d("TAG", text)}, "Save"
            )

        }
    }
}

@Composable
fun ListOfCurrency(
    selectedCurrency: String,
    onCurrencyClicked: (String) -> Unit,
    expanded: Boolean,
    onSelect: () -> Unit,
    toggledSelection: (Boolean) -> Unit
) {

    val currency = listOf(
        "$ US Dollar (USD) ",
        "€  Euro EUR",
        "£  British Pound Sterling GBP",
        "¥ Japanese Yen JPY",
        "CHF  Swiss Franc CHF",
        "C$  Canadian Dollar CAD",
        "A$  Australian Dollar AUD",
        "¥  Chinese Yuan Renminbi CNY",
        "₹  Indian Rupee INR",
        "R  South African Rand ZAR"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(20.dp)
                .clickable { toggledSelection(expanded) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCurrency,
                modifier = Modifier
                    .clickable { onSelect() }
                    .weight(5f)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {},
                modifier = Modifier
                    .width(360.dp)
                    .background(Color.White)
                    .padding(10.dp),
            ) {
                currency.forEach { currency ->
                    DropdownMenuItem(
                        onClick = {
                            onCurrencyClicked(currency)
                            toggledSelection(expanded)
                        },
                        text = {
                            Text(text = currency)
                        })
                }
            }
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreferenceScreenPreview() {
    PreferenceScreen(state = TransactionState(), {})
}