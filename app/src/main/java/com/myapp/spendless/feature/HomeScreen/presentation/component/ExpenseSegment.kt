package com.myapp.spendless.feature.HomeScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.model.Categories
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import com.myapp.spendless.feature.HomeScreen.model.categories
import com.myapp.spendless.feature.HomeScreen.presentation.state.TransactionState
import com.myapp.spendless.feature.Setting.toExpensesUnit
import com.myapp.spendless.ui.theme.Error
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryFixed
import java.util.UUID


@Composable
fun ExpenseSegment(
    transaction: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    selectedCategory: Categories,
    expanded: Boolean,
    onCategorySelect: (Categories) -> Unit,
    onEventHandle: (Int) -> Unit,
    onExpenseCreate: () -> Unit,
    onToggleClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
                        textAlign = TextAlign.Center,
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
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
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
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.fig_tree_regular
                                )
                            )
                        )
                    },
                    prefix = {
                        Text(" - $", color = Error)
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
                placeholder = {
                    Text(
                        text = "+ Add note",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
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

            Spacer(modifier = Modifier.height(20.dp))
            //DropDown
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(4.dp)
                    .clickable { onToggleClicked() },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            PrimaryFixed.copy(alpha = 0.8f),
                            RoundedCornerShape(12.dp)
                        )
                        .width(30.dp)
                        .height(44.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            selectedCategory.icon
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)

                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selectedCategory.category,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .weight(5f)
                )

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.weight(1f)
                )
            }
            CategoryDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onToggleClicked() },
                categories = categories,
                onCategorySelect = {
                    onCategorySelect(it)
                    onToggleClicked()
                },
                onEventHandle = onEventHandle
            )


            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onExpenseCreate()
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

@Composable
fun CategoryListItem(
    onToggleClicked: () -> Unit,
    selectedCategory: Categories
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(16.dp)
            )
            .padding(4.dp)
            .clickable { onToggleClicked() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    PrimaryFixed.copy(alpha = 0.8f),
                    RoundedCornerShape(12.dp)
                )
                .width(30.dp)
                .height(44.dp)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    selectedCategory.icon
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)

            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = selectedCategory.category,
            modifier = Modifier
                .weight(5f)
        )
    }
}


@Composable
fun CategoryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    categories: List<Categories>,
    onCategorySelect: (Categories) -> Unit,
    onEventHandle: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
                .width(330.dp)
                .height(400.dp)
                .background(Color.White)

        ) {
            categories.forEachIndexed { index, category ->
                CategoryListItem(
                    onToggleClicked = {
                        onCategorySelect(category)
                        onEventHandle(index)
                        onDismissRequest()
                    },
                    selectedCategory = category
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryMenuScreenPreview() {
    CategoryDropdownMenu(
        expanded = true,
        onDismissRequest = { },
        categories = categories,
        onCategorySelect = { }
    ) { }

}

@Preview(showBackground = true)
@Composable
fun CategoryListItemScreenPreview() {
    CategoryListItem(
        {}, selectedCategory = Categories(
            category = "Education",
            icon = R.drawable.education
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ExpenseSegmentScreenPreview() {
    val transactionState = TransactionState(
        transaction = Transaction(
            userId = UUID.randomUUID(),
            id = 2117,
            title = "erat",
            amount = 44.45,
            note = "Note is here.",
            category = "Education",
            icon = R.drawable.education,
            date = 7241
        )
    )

    ExpenseSegment(
        transaction = transactionState,
        onEvent = {},
        selectedCategory = Categories(
            category = "Education",
            icon = R.drawable.education
        ), expanded = false,
        onCategorySelect = {},
        onEventHandle = {},
        onToggleClicked = {},
        onExpenseCreate = {})
}
  