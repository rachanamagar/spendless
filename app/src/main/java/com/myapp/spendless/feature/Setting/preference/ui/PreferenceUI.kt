package com.myapp.spendless.feature.Setting.preference.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.presentation.component.AppButton
import com.myapp.spendless.feature.Setting.preference.model.AmountFormat
import com.myapp.spendless.feature.Setting.preference.model.DecimalSeparator
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.Setting.preference.model.ThousandSeparator
import com.myapp.spendless.feature.Setting.preference.viewModel.PreferencesScreenState
import com.myapp.spendless.feature.Setting.preference.viewModel.PreferencesViewModel
import com.myapp.spendless.util.DataStoreManager

@Composable
fun PreferenceUI(
   // paddingValues: PaddingValues,
    uiState: PreferencesScreenState,
    viewModel: PreferencesViewModel,
    selectedCurrency: String,
    onSelected:(String)-> Unit,
    expanded: Boolean,
    symbol: String,
    onSymbol:(String) -> Unit,
    onExpanded:(Boolean)-> Unit,
    onBack:()-> Unit,
    onSave:(String) -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
           // .padding(paddingValues)
            .padding(20.dp)
    ) {
        TotalAmountBox(uiState.displayTotalAmount, symbol)
        Spacer(modifier = Modifier.height(16.dp))

        ExpenseAmountFormat(){
            viewModel.changeAmountFormat(it)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Currency",
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ListOfCurrency(
            selectedCurrency = selectedCurrency,
            onCurrencyClicked = onSelected,
            onCurrencySymbol = onSymbol,
            expanded = expanded,
            toggledSelection = onExpanded,
        )

        Spacer(modifier = Modifier.height(20.dp))

        DecimalFormatBox {
            viewModel.changeDecimalSeperator(it)
        }
        Spacer(modifier = Modifier.height(20.dp))
        ThousandSeperatorBox { viewModel.changeThousandSeperator(it) }
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            modifier = Modifier
                .clickable {
                    viewModel.updatePriceDisplayConfig(
                        uiState.priceDisplayConfig.amountFormat,
                        uiState.priceDisplayConfig.decimalSeparator,
                        uiState.priceDisplayConfig.thousandSeparator
                    )
                    viewModel.savePreference()
                    viewModel.saveSymbolPreference(symbol)
                    onSave(symbol)
                    onBack()
                    Log.d("Preferences", "Selected Price Display Config: ${uiState.priceDisplayConfig}")
                },
            "Save"
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreferneceUIPreview(){
    val context = LocalContext.current
    PreferenceUI(
        uiState = PreferencesScreenState(
            priceDisplayConfig = PriceDisplayConfig(
                amountFormat = AmountFormat.WithBrackets,
                decimalSeparator = DecimalSeparator.Dot,
                thousandSeparator = ThousandSeparator.Comma
            ), displayTotalAmount = "10000"
        ),
        viewModel = PreferencesViewModel(
            dataStoreManager = DataStoreManager(context =  context)
        ),
        selectedCurrency = "US dollar",
        onSelected = {},
        expanded = false,
        symbol = "$",
        onSymbol = {},
        onExpanded = {},
        onBack = {}) { }
}

