package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.SecondaryContainer
import com.myapp.spendless.ui.theme.SecondaryFixed
import com.myapp.spendless.ui.theme.SurfaceBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(getName: String) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = getName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
                    .height(202.dp)
                    .width(380.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "$10,000.45", fontSize = 45.sp,
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
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(240.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryFixed, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Your Largest transaction will appear here",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                        )
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
                            "$0",
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
            ButtomRow()
        }
    }
}

@Composable
fun ButtomRow() {
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ButtomRowPreview() {
    ButtomRow()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen("user1")
}