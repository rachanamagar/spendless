package com.myapp.spendless.feature.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.PrimaryText

@Composable
fun SegmentedAppButton(
    list: List<String>,
    selectedIndex: Int,
    onSelectedIndex: (Int) -> Unit,
    onItemClicked: (Int) -> Unit
) {

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryFixed, RoundedCornerShape(14.dp))
            .padding(4.dp)
    ) {
        list.forEachIndexed { index, item ->
            SegmentedButton(
                // modifier = Modifier.background(Color.White, RoundedCornerShape(10.dp)),
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = list.size,
                    RoundedCornerShape(16.dp)
                ),
                onClick = {
                    onSelectedIndex(index)
                    onItemClicked(index)
                },
                selected = index == selectedIndex,
                label = { Text(item, color = PrimaryText) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = Color.White,
                    inactiveContainerColor = Color.Transparent,
                    disabledActiveBorderColor = Color.Transparent,
                    inactiveBorderColor = Color.Transparent,
                    activeBorderColor = Color.Transparent
                ),
                icon = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 2.dp)

            )
        }
    }
}


@Composable
fun TransactionMethodButton(
    list: List<Menu>,
    selectedIndex: Int,
    onSelectedIndex: (Int) -> Unit,
    onItemClicked: (Int) -> Unit
) {

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryFixed, RoundedCornerShape(14.dp))
            .padding(4.dp)
    ) {
        list.forEachIndexed { index, menu ->
            SegmentedButton(
                // modifier = Modifier.background(Color.White, RoundedCornerShape(10.dp)),
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = list.size,
                    RoundedCornerShape(16.dp)
                ),
                onClick = {
                    onSelectedIndex(index)
                    onItemClicked(index)
                },
                selected = index == selectedIndex,
                label = { Text(menu.item, color = PrimaryText) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = Color.White,
                    inactiveContainerColor = Color.Transparent,
                    disabledActiveBorderColor = Color.Transparent,
                    inactiveBorderColor = Color.Transparent,
                    activeBorderColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        painter = painterResource(menu.icon),
                        contentDescription = null,
                        tint = PrimaryText,
                        modifier = Modifier.size(15.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 2.dp)

            )
        }
    }
}

data class Menu(
    val item: String,
    val icon: Int
)

@Preview(showBackground = true)
@Composable
fun SSegmentedButtonScreenPreview() {
    val list = listOf("a", "b ", "c")
    SegmentedAppButton(list, 1, {}) {}
}