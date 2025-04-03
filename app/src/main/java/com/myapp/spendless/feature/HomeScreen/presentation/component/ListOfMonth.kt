package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R

@Composable
fun ListOfMonth(
    onItemClicked: (String) -> Unit,
    onBack:()-> Unit
) {
    var month by remember { mutableStateOf("Select Month") }
    val list = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
    val filterMonths = list.take(currentMonthIndex + 1)
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable { isExpanded = true },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back options",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onBack()}
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Specific month", fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.fig_tree_regular)),
                modifier = Modifier
                    .clickable { isExpanded = true }
            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
    if (isExpanded) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            itemsIndexed(filterMonths) { index, item ->
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
                            month = item
                            isExpanded = false
                            onItemClicked(item)
                        }
                    )
                }
            }
        }
    }
}