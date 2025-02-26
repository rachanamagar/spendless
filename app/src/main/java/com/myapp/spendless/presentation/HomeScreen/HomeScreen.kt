package com.myapp.spendless.presentation.HomeScreen

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
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.presentation.HomeScreen.ui.TransactionList
import com.myapp.spendless.presentation.component.TransactionLayout
import com.myapp.spendless.presentation.setting.formatTotalAmount
import com.myapp.spendless.presentation.setting.preference.viewModel.PreferencesViewModel
import com.myapp.spendless.presentation.setting.toExpensesUnit
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.PrimaryText
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.ui.theme.SecondaryFixed
import com.myapp.spendless.ui.theme.SurfaceBackground
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

    LaunchedEffect(Unit) {
        viewModel.getAllTransaction()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = userName ?: "Guest",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
                        )

                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(PrimaryFixed.copy(alpha = 0.5f))
                                .clickable { onSetting() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.setting),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp)
                            )
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
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
                .background(Primary)
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
                            text = state.symbol,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                            fontSize = 30.sp,
                            color = Color.White
                        )

                        Text(
                            formatTotalAmount(
                                state.totalAmount.toString(),
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

                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(132.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SecondaryFixed, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Text(
                            text = if (state.transactionList.isNotEmpty()) state.lastWeek.toString()
                                .toExpensesUnit(
                                    Color.Black
                                ).toString() else "0.0",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
                        Text(
                            "Previous week",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
                    }
                }
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

fun formatDateLabel(timestamp: Long): String {
    val transactionDate = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()

    return when (transactionDate) {
        today -> "Today"
        today.minusDays(1) -> "Yesterday"
        else -> transactionDate.format(DateTimeFormatter.ofPattern("MMMM dd"))
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}) {}
}