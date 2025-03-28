package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.model.Categories
import com.myapp.spendless.feature.HomeScreen.model.categories
import com.myapp.spendless.feature.HomeScreen.presentation.state.TransactionState
import com.myapp.spendless.feature.HomeScreen.presentation.viewmodel.TransactionViewModel
import com.myapp.spendless.feature.Setting.Menu
import com.myapp.spendless.feature.Setting.TransactionMethodButton
import com.myapp.spendless.ui.theme.SurfaceBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransaction(onBack: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Categories?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ModalBottomSheet(
            onDismissRequest = {
                onBack()
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize().padding(top = 50.dp)
        ) {
            ModalSheetBottom(
                transaction = state,
                onEvent = viewModel::handleEvent,
                selectedCategory = selectedCategory,
                onCategorySelect = { category ->
                    selectedCategory = category
                    isExpanded = false

                },
                onIncomeCreated = {
                    if (state.transaction.title.isEmpty() || state.transaction.note.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please enter the transaction details.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        viewModel.insertTransaction()
                        onBack()
                    }
                },
                expanded = isExpanded,
                onDropdownToggle = { isExpanded = !isExpanded },
                onEventHandle = {
                    viewModel.handleEvent(TransactionEvent.updateCategory(categories[it].category))
                    viewModel.handleEvent(TransactionEvent.updateIcon(categories[it].icon))
                },
                onExpenseCreated = {
                    if (state.transaction.title.isEmpty() || state.transaction.note.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please enter the transaction details.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{ viewModel.insertTransaction()
                        onBack()
                    }
                },
                onTabChange = { viewModel.resetState() }
            )
        }
    }
}


@Composable
fun ModalSheetBottom(
    transaction: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    onIncomeCreated: () -> Unit,
    onExpenseCreated: () -> Unit,
    selectedCategory: Categories?,
    onCategorySelect: (Categories) -> Unit,
    expanded: Boolean,
    onDropdownToggle: () -> Unit,
    onEventHandle: (Int) -> Unit,
    onTabChange: () -> Unit
) {

    val buttonList = listOf(Menu("Expense", R.drawable.down), Menu("Income", R.drawable.up))
    val keyboardController = LocalSoftwareKeyboardController.current
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
           // .fillMaxWidth()
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(20.dp)
    ) {
        Text(
            text = "Create Transaction",
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
        )

        Spacer(modifier = Modifier.height(8.dp))
        TransactionMethodButton(
            list = buttonList,
            selectedIndex = selectedIndex,
            onSelectedIndex = { selectedIndex = it }
        ) {
            selectedIndex = it
            onTabChange()

        }
        when (selectedIndex) {
            1 -> {
                IncomeSegment(
                    transaction = transaction,
                    onEvent = { onEvent(it) },
                    onIncomeCreate = { onIncomeCreated() }
                )
            }


            0 -> {
                ExpenseSegment(
                    transaction = transaction,
                    onEvent = { onEvent(it) },
                    selectedCategory = selectedCategory ?: Categories(
                        "Other",
                        R.drawable.other
                    ),
                    expanded = expanded,
                    onCategorySelect = { onCategorySelect(it) },
                    onEventHandle = { onEventHandle(it) },
                    onToggleClicked = { onDropdownToggle() },
                    onExpenseCreate = { onExpenseCreated() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewTransactionScreenPreview() {
}