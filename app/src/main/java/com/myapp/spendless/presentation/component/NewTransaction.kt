package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.spendless.R
import com.myapp.spendless.model.Categories
import com.myapp.spendless.model.categories
import com.myapp.spendless.presentation.setting.toExpensesUnit
import com.myapp.spendless.presentation.state.TransactionState
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel
import com.myapp.spendless.ui.theme.Error
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.Success
import com.myapp.spendless.ui.theme.SurfaceBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransaction(onCreateClicked: () -> Unit, onBack: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
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
            modifier = Modifier.fillMaxSize()
        ) {
            ModalSheetBottom(
                transaction = state,
                onEvent = viewModel::handleEvent,
                selectedCategory = selectedCategory,
                onCategorySelect = { category ->
                    selectedCategory = category
                    isExpanded = false
                },
                onCreateClicked = {
                    onCreateClicked()
                },
                expanded = isExpanded,
                onExpandToggle = { isExpanded = !isExpanded },
                onEventHandle = {
                    viewModel.handleEvent(TransactionEvent.updateCategory(categories[it].category))
                    viewModel.handleEvent(TransactionEvent.updateIcon(categories[it].icon))
                }
            )
        }
    }
}


@Composable
fun ModalSheetBottom(
    transaction: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    onCreateClicked: () -> Unit,
    selectedCategory: Categories?,
    onCategorySelect: (Categories) -> Unit,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
    onEventHandle: (Int) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var selectedIndex by remember { mutableIntStateOf(0) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceBackground)
            .height(600.dp)
            .padding(20.dp)
    ) {
        Text(
            text = "Create Transaction",
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            SegmentedButton(selectedIndex = selectedIndex, modifier = Modifier.fillMaxWidth()) {
                selectedIndex = it
            }
        }

        when (selectedIndex) {
            1 -> {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            OutlinedTextField(
                                value = transaction.transaction.title,
                                onValueChange = { newText ->
                                    onEvent(TransactionEvent.updateTitle(newText))
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                ),
                                placeholder = {
                                    Text(
                                        text = "Sender",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.fig_tree_regular
                                            )
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                                    .onFocusChanged { focusState ->
                                        if (focusState.isFocused) {
                                            keyboardController?.show()
                                        } else {
                                            keyboardController?.hide()
                                        }
                                    },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("$", color = Success)
                                OutlinedTextField(
                                    value = transaction.transaction.amount.toString(),
                                    onValueChange = { newAmount: String ->
                                        val amount = newAmount.toDoubleOrNull() ?: 0.0
                                        onEvent(TransactionEvent.updateAmount(amount))
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "0.0",
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(
                                                    R.font.fig_tree_regular
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(focusRequester)
                                        .onFocusChanged { focusState ->
                                            if (focusState.isFocused) {
                                                keyboardController?.show()
                                            } else {
                                                keyboardController?.hide()
                                            }
                                        },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    )

                                )
                            }

                            OutlinedTextField(
                                value = transaction.transaction.note,
                                onValueChange = { newNote ->
                                    onEvent(TransactionEvent.updateNote(newNote))
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                ),
                                label = {
                                    Text(
                                        text = "+ Add note",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.fig_tree_regular
                                            )
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                                    .onFocusChanged { focusState ->
                                        if (focusState.isFocused) {
                                            keyboardController?.show()
                                        } else {
                                            keyboardController?.hide()
                                        }
                                    },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White, RoundedCornerShape(16.dp))
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                PrimaryFixed.copy(alpha = 0.02f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .width(30.dp)
                                            .height(44.dp)
                                            .aspectRatio(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                   transaction.transaction.icon
                                            ),
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(30.dp)

                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = transaction.transaction.category,
                                        modifier = Modifier
                                            .clickable { onExpandToggle() }
                                            .weight(5f)
                                    )
                                }
                            }
                        }
                    }

                    if (selectedCategory == null) {
                        onCategorySelect(Categories("Income", R.drawable.income))
                    }

                    Button(
                        onClick = {
                            onCreateClicked()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, RoundedCornerShape(8.dp))
                            .padding(top = 30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        )
                    ) {
                        Text(text = "Create")
                    }
                }
            }

            0 -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                OutlinedTextField(
                                    value = transaction.transaction.title,
                                    onValueChange = { newText ->
                                        onEvent(TransactionEvent.updateTitle(newText))
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Title",
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(
                                                    R.font.fig_tree_regular
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(focusRequester)
                                        .onFocusChanged { focusState ->
                                            if (focusState.isFocused) {
                                                keyboardController?.show()
                                            } else {
                                                keyboardController?.hide()
                                            }
                                        },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(" - $", color = Error)
                                    OutlinedTextField(
                                        value = transaction.transaction.amount.toString(),
                                        onValueChange = { newAmount: String ->
                                            val amount = newAmount.toDoubleOrNull() ?: 0.0
                                            onEvent(TransactionEvent.updateAmount(amount))
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = Color.Transparent,
                                            focusedBorderColor = Color.Transparent
                                        ),
                                        placeholder = {
                                            Text(
                                                text = "0.0".toExpensesUnit(Error),
                                                color = Color.Gray,
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(
                                                    Font(
                                                        R.font.fig_tree_regular
                                                    )
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .focusRequester(focusRequester)
                                            .onFocusChanged { focusState ->
                                                if (focusState.isFocused) {
                                                    keyboardController?.show()
                                                } else {
                                                    keyboardController?.hide()
                                                }
                                            },
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Next
                                        )


                                    )
                                }

                                OutlinedTextField(
                                    value = transaction.transaction.note,
                                    onValueChange = { newNote ->
                                        onEvent(TransactionEvent.updateNote(newNote))
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent
                                    ),
                                    label = {
                                        Text(
                                            text = "+ Add note",
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(
                                                    R.font.fig_tree_regular
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(focusRequester)
                                        .onFocusChanged { focusState ->
                                            if (focusState.isFocused) {
                                                keyboardController?.show()
                                            } else {
                                                keyboardController?.hide()
                                            }
                                        },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    )
                                )
                                //DropDown
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White, RoundedCornerShape(16.dp))
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .background(
                                                    PrimaryFixed.copy(alpha = 0.02f),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .width(30.dp)
                                                .height(44.dp)
                                                .aspectRatio(1f),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(
                                                    if (selectedCategory == null) {
                                                        R.drawable.other
                                                    } else selectedCategory.icon
                                                ),
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                                modifier = Modifier.size(30.dp)

                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (selectedCategory == null) "Other" else selectedCategory.category,
                                            modifier = Modifier
                                                .clickable { onExpandToggle() }
                                                .weight(5f)
                                        )

                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier.weight(1f)
                                        )

                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = {},
                                            modifier = Modifier
                                                .width(360.dp)
                                                .background(Color.White)
                                                .padding(10.dp),
                                        ) {
                                            categories.forEachIndexed { index, category ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        onCategorySelect(category)
                                                        onEventHandle(index)
                                                    },
                                                    leadingIcon = {
                                                        Icon(
                                                            painter = painterResource(id = category.icon),
                                                            contentDescription = null,
                                                            tint = Color.Unspecified,
                                                            modifier = Modifier.size(18.dp)
                                                        )
                                                    },
                                                    text = {
                                                        Text(text = category.category)
                                                    })
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onCreateClicked()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, RoundedCornerShape(8.dp))
                            .padding(top = 30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        )
                    ) {
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewTransactionScreenPreview() {
}