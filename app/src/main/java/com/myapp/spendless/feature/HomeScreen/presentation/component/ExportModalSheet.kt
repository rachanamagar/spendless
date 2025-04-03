package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun ExportModalSheet(onItemClicked: (Int, String) -> Unit, onExportClicked: (Int) -> Unit) {

    var text by remember { mutableStateOf("Last three months") }
    var isRangeExpanded by remember { mutableStateOf(false) }
    var isRangeSelected by remember { mutableStateOf(false) }
    var format by remember { mutableStateOf("CSV") }
    var isFormatExpanded by remember { mutableStateOf(false) }
    var formatIndex by remember { mutableStateOf(0) }
    var rangeIndex by remember { mutableStateOf(0) }
    var isSpecificMonthSelected by remember { mutableStateOf(false) }

    val list = listOf("Last three month", "Current month", "Last Month", "Specific month")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Text("Export", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.fig_tree_medium)))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Export transactions to CSV format",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Export Range",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.fig_tree_medium))
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text, fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.fig_tree_regular))
                )

                Icon(
                    painter = painterResource(R.drawable.downa),
                    contentDescription = "export options",
                    modifier = Modifier
                        .size(10.dp)
                        .clickable { isRangeExpanded = true }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (isRangeExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(10.dp)
            ) {
                itemsIndexed(list) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item, fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
                            modifier = Modifier.clickable {
                                text = item
                                rangeIndex = index
                                isRangeSelected = true
                                if (item == "Specific month") {
                                    isSpecificMonthSelected = true
                                }
                                isRangeExpanded = false
                                onItemClicked(index, item)
                                Log.d("ExportModalSheet", "Selected rangeIndex: $rangeIndex")
                            }
                        )
                    }
                }
            }
        }

        if (isSpecificMonthSelected) {
            ListOfMonth({
                text = it
                isSpecificMonthSelected = false

            },
                onBack = {
                    isSpecificMonthSelected = false
                    isRangeExpanded = true
                })
        }

        if (isRangeSelected) {

            Text(
                "Export format",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = format, fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.fig_tree_regular))
                    )

                    Icon(
                        painter = painterResource(R.drawable.downa),
                        contentDescription = "export options",
                        modifier = Modifier
                            .size(10.dp)
                            .clickable { isFormatExpanded = true }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            val formatList = listOf("CSV", "PDF")

            if (isFormatExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(10.dp)
                    ) {
                        itemsIndexed(formatList) { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item, fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
                                    modifier = Modifier.clickable {
                                        format = item
                                        formatIndex = index
                                        isFormatExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onExportClicked(formatIndex)
            }, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text(
                text = "Export",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportModal(onRangeClicked: (Int, String) -> Unit, onExportClicked: (Int) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ModalBottomSheet(
            onDismissRequest = {},
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            ExportModalSheet(onItemClicked = onRangeClicked, onExportClicked = onExportClicked)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExportModalSheetPreview() {
    // ExportModalSheet({},{})
}