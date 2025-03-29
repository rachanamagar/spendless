package com.myapp.spendless.feature.HomeScreen.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.spendless.R
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import com.myapp.spendless.feature.HomeScreen.presentation.component.TransactionLayout
import com.myapp.spendless.feature.Setting.formatTotalAmount
import com.myapp.spendless.feature.Setting.preference.viewModel.PreferencesViewModel
import com.myapp.spendless.feature.Setting.toExpensesUnit
import com.myapp.spendless.feature.HomeScreen.presentation.viewmodel.TransactionViewModel
import com.myapp.spendless.feature.Setting.toDollar
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.PrimaryOne
import com.myapp.spendless.ui.theme.PrimaryText
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.ui.theme.SecondaryFixed
import com.myapp.spendless.ui.theme.SurfaceBackground
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSetting: () -> Unit, onCLicked: () -> Unit, onShowAll: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    val popular by viewModel.popularCategory.collectAsState()
    val list = state.transactionList
    val userName by viewModel.userName.collectAsStateWithLifecycle()

    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    val uiPreferences by preferencesViewModel.uiPreferenceState.collectAsStateWithLifecycle()
    val currency by preferencesViewModel.currencySymbol.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllTransaction()
        preferencesViewModel.loadPreference()
        viewModel.getPreviousWeekTransaction()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppTopBar(userName ?: "Guest") {
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
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add transaction")
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
                            text = currency ?: state.symbol,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            fontSize = 30.sp,
                            color = Color.White
                        )

                        Text(
                            formatTotalAmount(
                                state.totalAmount.formatToTwoDecimal(),
                                priceDisplayConfig = uiPreferences.priceDisplayConfig,
                            ),
                            fontSize = 45.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
                    }
                    Text(
                        "Account Balance",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                }
            }

            popular?.let { HighestTransactionSection(popular) }

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
                                "Your Largest transaction will appear here",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                PreviousWeekTransaction(state.lastWeek.formatToTwoDecimal().toDollar())
            }
            ButtomRow(
                list = list
            ) { onShowAll() }
        }
    }
}

@Composable
fun ButtomRow(list: List<Transaction>, onShowAll: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryContainer.copy(alpha = 0.1f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                // .height(442.dp)
                .background(SurfaceBackground, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (list.isEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    "No transactions to show",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Last Transaction",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            modifier = Modifier
                        )
                        Text(text = "Show all",
                            fontSize = 14.sp,
                            color = PrimaryText,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            modifier = Modifier.clickable { onShowAll() })
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TransactionList(list)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtomRowPreview() {
    val cat = listOf(
        Transaction(
            id = 1,
            title = "Decoration",
            amount = "2000.0".toDouble(),
            note = "Home decoration expenses",
            icon = R.drawable.home,
            category = "Home",
            date = 2,
            userId = UUID.randomUUID()
        ), Transaction(
            id = 1,
            title = "Transportation",
            amount = "2000.0".toDouble(),
            note = "Home decoration expenses",
            icon = R.drawable.transport,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        ),
        Transaction(
            id = 1,
            title = "Food",
            amount = "2000.0".toDouble(),
            note = "Groceries",
            icon = R.drawable.food,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        ), Transaction(
            id = 1,
            title = "Education",
            amount = "2000.0".toDouble(),
            note = "Course Book Purchase",
            icon = R.drawable.education,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        )
    )
    ButtomRow(
        list = cat
    ) {}
}

fun Double.formatCurrency(): String {
    val absValue = kotlin.math.abs(this)
    val formattedValue = String.format(Locale.US, "%.2f", absValue)

    return if (this < 0) "- $${formattedValue}" else "$${formattedValue}"
}

fun Double.formatToTwoDecimal(): String {
    return String.format(Locale.US, "%.2f", this)
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}) {}
}