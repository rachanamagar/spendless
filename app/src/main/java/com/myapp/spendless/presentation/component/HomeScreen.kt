package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer

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
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_semi_bold))
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary)
                .padding(paddingValues)
                .padding(20.dp)
        ) {


        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen("user1")
}