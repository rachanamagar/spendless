package com.myapp.spendless.feature.Setting.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.model.SessionExpiry
import com.myapp.spendless.feature.HomeScreen.presentation.component.AppButton
import com.myapp.spendless.feature.Setting.SegmentedAppButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(onBackPressed: () -> Unit, navigateToLogin:()-> Unit) {

    val viewModel: SessionViewModel = hiltViewModel()
    val state by viewModel.sessionState.collectAsStateWithLifecycle()

    val list = listOf("5 min", "15 min", "30 min", "1 hour")
    val lockedOutDurationList = listOf("15s", "30s", "1 min", "5 min")

    val sessionExpiryMap = mapOf(
        0 to SessionExpiry.FirstDuration,
        1 to SessionExpiry.SecondDuration,
        2 to SessionExpiry.ThirdDuration,
        3 to SessionExpiry.FourthDuration
    )
    var selectedIndex by remember {
        mutableIntStateOf(
            sessionExpiryMap.entries.firstOrNull { it.value == state.expiryDuration.duration }?.key
                ?: 0
        )
    }

    var lockedSelectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { onBackPressed() })
                },
                title = {
                    Text(
                        text = "Security",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_medium))
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(20.dp),

            ) {
            Text(
                "Session expiry duration ",
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            SegmentedAppButton(
                list = list,
                selectedIndex = selectedIndex,
                onSelectedIndex = { index ->
                    selectedIndex = index
                },
                onItemClicked = { index ->
                    sessionExpiryMap[index]?.let { viewModel.setSessionExpiry(it) }
                    Log.d("SecurityScreen", "User selected session expiry: ${list[index]}")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Locked out duration ", modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            SegmentedAppButton(lockedOutDurationList,
                lockedSelectedIndex,
                {}) {
                lockedSelectedIndex = it
            }

            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                modifier = Modifier
                    .clickable { onBackPressed() }, "Save"
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecurityScreenPreview() {
    SecurityScreen({}){}
}