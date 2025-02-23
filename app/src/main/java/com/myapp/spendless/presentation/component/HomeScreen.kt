package com.myapp.spendless.presentation.component

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.myapp.spendless.R
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.ui.theme.SecondaryFixed
import com.myapp.spendless.ui.theme.SurfaceBackground
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(getName: String, onSetting:()-> Unit, onCLicked: () -> Unit) {

    val viewModel: TransactionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    val list = state.transactionList

    LaunchedEffect(Unit) {
        viewModel.getAllTransaction()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = getName,
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
                                .clickable { onSetting() }
                                ,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painter = painterResource(R.drawable.setting),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp).padding(2.dp))
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
            //.padding(20.dp)
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Text(
                        formatAmount(state.totalAmount).toDollar(), fontSize = 45.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                    )
                    Text(
                        "Account Balance",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(240.dp)
                        .background(PrimaryFixed, RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.transactionList.isNotEmpty()) {
                            state.maxTransaction?.let { TransactionLayout(it) }
                        } else {
                            Text(
                                "Your Largest transaction will appear here",
                                fontSize = 12.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
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
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
                        Text(
                            "Previous week",
                            fontSize = 10.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
                    }
                }
            }
            ButtomRow(
                list = list
            )
        }
    }
}

@Composable
fun ButtomRow(list: List<Transaction>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryFixed, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                // .height(442.dp)
                .background(SurfaceBackground, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
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
                    Text(
                        "Last Transaction",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(list) { transaction ->
                            TransactionListItem(transaction)
                        }
                    }
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
            date = 1,
            userId = UUID.randomUUID()
        ), Transaction(
            id = 1,
            title = "Decoration",
            amount = "2000.0".toDouble(),
            note = "Home decoration expenses",
            icon = R.drawable.home,
            category = "Home",
            date = 1,
            userId = UUID.randomUUID()
        )
    )
    ButtomRow(
        list = cat
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen("user1", {}) {}
}