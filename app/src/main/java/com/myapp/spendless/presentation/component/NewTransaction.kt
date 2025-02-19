package com.myapp.spendless.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.myapp.spendless.R
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel
import com.myapp.spendless.ui.theme.PrimaryFixed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransaction( onCreateClicked: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    var showButtomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ModalBottomSheet(
            onDismissRequest = {
                showButtomSheet = false
                coroutineScope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            containerColor = PrimaryFixed
        ) {
            ModalSheetBottom(
                transaction = state,
                onEvent = viewModel::handleEvent
            ) { onCreateClicked()
            showButtomSheet = false}
        }
    }
}

@Composable
fun ModalSheetBottom(
    transaction: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    onCreateClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(
            text = "Create Transaction",
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            SegmentedButton(modifier = Modifier.fillMaxWidth())
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    .align(Alignment.CenterHorizontally)
            )

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
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "+ Add Note",
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
            )

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
                        text = "Description",
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
                    .align(Alignment.CenterHorizontally)
            )

            Button(onClick = {onCreateClicked()
            }) {
                Text(text = "Create")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewTransactionScreenPreview() {
}