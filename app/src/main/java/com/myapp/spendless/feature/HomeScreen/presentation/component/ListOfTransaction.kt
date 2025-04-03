package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.presentation.viewmodel.TransactionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfTransaction(onBack: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val list = state.transactionList
    val context = LocalContext.current
    var isExported by remember { mutableStateOf(false) }
    var isRangeSelected by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var sortedList by remember { mutableStateOf(list) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { onBack() })
                    }
                },
                title = {
                    Text(
                        text = "All Transactions",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.download),
                        contentDescription = "Download Button",
                        modifier = Modifier
                            .padding(20.dp)
                            .size(30.dp)
                            .clickable {
                                isExported = true
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(10.dp)
        ) {

            TransactionList(list)
        }
    }

    if (isExported) {
        ExportModal(onRangeClicked = { range, month ->
            sortedList = when (range) {
                0 -> {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.MONTH, -3)
                    val threeMonthAgo = calendar.timeInMillis
                    list.filter { it.date >= threeMonthAgo }
                }

                1 -> {
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1)
                        clear(Calendar.HOUR_OF_DAY)
                        clear(Calendar.MINUTE)
                        clear(Calendar.SECOND)
                        clear(Calendar.MILLISECOND)
                    }
                    val startOfMonth = calendar.timeInMillis
                    list.filter { it.date >= startOfMonth }
                }

                2 -> {
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1)
                        clear(Calendar.HOUR_OF_DAY)
                        clear(Calendar.MINUTE)
                        clear(Calendar.SECOND)
                        clear(Calendar.MILLISECOND)

                    }
                    val endOfLastMonth = calendar.timeInMillis
                    calendar.add(Calendar.MONTH, -1)
                    val startOfLastMonth = calendar.timeInMillis

                    list.filter { it.date in startOfLastMonth until endOfLastMonth }
                }

                else -> { list
                }
            }
            Log.d("list", "Sorted list size: ${sortedList.size}")
        }
        )
        {
            if (it == 0) {
                exportTransactionToCSV(context, sortedList)
                Toast.makeText(
                    context,
                    "CSV exported to Downloads",
                    Toast.LENGTH_SHORT
                ).show()
                coroutineScope.launch {
                    delay(2000L)
                    isExported = false
                }
            } else {
                exportTransactionToPDF(context, sortedList)
                Toast.makeText(
                    context,
                    "PDF exported to Downloads",
                    Toast.LENGTH_SHORT
                ).show()
                coroutineScope.launch {
                    delay(2000L)
                    isExported = false
                }
            }
        }
    }
}