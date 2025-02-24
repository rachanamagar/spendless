package com.myapp.spendless.presentation.setting.preference

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapp.spendless.R
import com.myapp.spendless.presentation.component.AppButton
import com.myapp.spendless.presentation.setting.preference.ui.DecimalFormatBox
import com.myapp.spendless.presentation.setting.preference.ui.ExpenseAmountFormat
import com.myapp.spendless.presentation.setting.preference.ui.ThousandSeperatorBox
import com.myapp.spendless.presentation.setting.preference.ui.TotalAmountBox
import com.myapp.spendless.presentation.setting.preference.viewModel.PreferencesScreenState
import com.myapp.spendless.presentation.setting.preference.viewModel.PreferencesViewModel

@Composable
fun PreferenceUI(
    paddingValues: PaddingValues,
    uiState: PreferencesScreenState,
    viewModel: PreferencesViewModel,
    selectedCurrency: String,
    expanded: Boolean,
    navController: NavHostController,
    onSave:(String)-> Unit
) {
    var selectedCurrency1 = selectedCurrency
    var expanded1 = expanded

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(20.dp)
    ) {
        TotalAmountBox(uiState.displayTotalAmount)
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
            selectedCurrency = selectedCurrency1,
            { selectedCurrency1 = it },
            expanded1,
            { expanded1 = !expanded1 },
            toggledSelection = { expanded1 = !expanded1 }
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
                    onSave(uiState.displayTotalAmount)
                    navController.popBackStack()
                    Log.d("TAG", "")
                }, "Save"
        )

    }
}

