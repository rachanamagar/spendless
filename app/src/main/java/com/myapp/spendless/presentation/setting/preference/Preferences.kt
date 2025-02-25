package com.myapp.spendless.presentation.setting.preference

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.presentation.setting.preference.viewModel.PreferencesViewModel
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendlessPreferenceScreen(onSave:(String)-> Unit, onBackPressed: () -> Unit) {

    val viewModel: PreferencesViewModel = hiltViewModel()
    val uiState by viewModel.uiPreferenceState.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    var symbol by remember { mutableStateOf("$") }
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
        PreferenceUI(
            paddingValues,
            uiState,
            viewModel,
            selectedCurrency = selectedCurrency,
            onSelected = { selectedCurrency = it },
            expanded = expanded,
            onExpanded = { expanded = it },
            onBack = { onBackPressed() },
            symbol = symbol,
            onSymbol = { symbol = it},
            onSave = { onSave(symbol)}
        )
    }
}
