package com.myapp.spendless.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.formatTotalAmount
import com.myapp.spendless.feature.Setting.toDollar
import com.myapp.spendless.feature.home.presentation.component.AppTopBar
import com.myapp.spendless.feature.home.presentation.component.BottomRow
import com.myapp.spendless.feature.home.presentation.component.HighestTransactionSection
import com.myapp.spendless.feature.home.presentation.component.PreviousWeekTransaction
import com.myapp.spendless.feature.home.presentation.component.TransactionLayout
import com.myapp.spendless.feature.home.presentation.viewmodel.HomeViewModel
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.PrimaryOne
import com.myapp.spendless.ui.theme.PrimaryText
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.util.formatToTwoDecimal
import com.myapp.spendless.util.toCurrencySymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSetting: () -> Unit, onCLicked: () -> Unit, onShowAll: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppTopBar(state.username ?: stringResource(R.string.guest)) {
                        onSetting()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCLicked() },
                containerColor = SecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_transaction)
                )
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryOne, PrimaryText),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(202.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = state.currency.toCurrencySymbol(),
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            fontSize = 30.sp,
                            color = Color.White
                        )

                        state.priceDisplayConfig?.let {
                            Text(
                                formatTotalAmount(
                                    state.totalAmount.formatToTwoDecimal(),
                                    priceDisplayConfig = it,
                                ),
                                fontSize = 45.sp,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            )
                        }

                    }
                    Text(
                        stringResource(R.string.account_balance),
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                }
            }

            state.popularCategory?.let { HighestTransactionSection(it) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(240.dp)
                        .background(PrimaryFixed, RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (state.transactionList.isNotEmpty()) {
                            state.maxTransaction?.let { TransactionLayout(it) }
                        } else {
                            Text(
                                stringResource(R.string.your_largest_transaction_will_appear_here),
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                PreviousWeekTransaction(
                    state.lastWeekTransactionAmount.formatToTwoDecimal().toDollar()
                )
            }
            BottomRow(
                list = state.transactionList
            ) { onShowAll() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}) {}
}