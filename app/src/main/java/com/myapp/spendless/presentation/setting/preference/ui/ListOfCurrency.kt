package com.myapp.spendless.presentation.setting.preference.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ListOfCurrency(
    selectedCurrency: String,
    onCurrencyClicked: (String) -> Unit,
    onCurrencySymbol:(String) -> Unit,
    expanded: Boolean,
    toggledSelection: (Boolean) -> Unit
) {

    val currency = mapOf(
        "$" to "US Dollar (USD) ",
        "€" to "Euro EUR",
        "£" to "British Pound SterlingGBP",
        "¥" to " Japanese Yen JPY",
        "CHF" to "Swiss Franc CHF",
        "C$" to "Canadian Dollar CAD",
        "A$" to "Australian Dollar AUD",
        "¥" to "Chinese Yuan Renminbi CNY",
        "₹" to "Indian Rupee INR",
        "R" to "South African Rand ZAR"
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
                .clickable { toggledSelection(!expanded) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCurrency,
                modifier = Modifier
                    .weight(5f)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { toggledSelection(false) },
                modifier = Modifier
                    .width(360.dp)
                    .background(Color.White)
                    .padding(10.dp),
            ) {
                currency.forEach { (symbol, currency) ->
                    DropdownMenuItem(
                        onClick = {
                            onCurrencyClicked(currency)
                            onCurrencySymbol(symbol)
                            toggledSelection(expanded)
                        },
                        leadingIcon = {
                            Text(text = symbol)
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
fun ListOfCurrencyScreenPreview() {
  ListOfCurrency(selectedCurrency = "inceptos", onCurrencyClicked = {}, {"$"}, expanded = false) { }
}
