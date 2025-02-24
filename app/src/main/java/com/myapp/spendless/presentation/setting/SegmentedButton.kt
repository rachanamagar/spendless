package com.myapp.spendless.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.spendless.ui.theme.Primary
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.PrimaryText

@Composable
fun SegmentedButton(list: List<String>, selectedIndex: Int, onSelectedIndex: (Int) -> Unit, onItemClicked:(Int)-> Unit) {

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryFixed, RoundedCornerShape(14.dp))
            .padding(4.dp)
    ) {
        list.forEachIndexed { index, time ->
            SegmentedButton(
                // modifier = Modifier.background(Color.White, RoundedCornerShape(10.dp)),
                shape = SegmentedButtonDefaults.itemShape(index = index, count = list.size, RoundedCornerShape(16.dp)),
                onClick = { onSelectedIndex(index)
                          onItemClicked(index)},
                selected = index == selectedIndex,
                label = { Text(time, color = PrimaryText) },
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


@Preview(showBackground = true)
@Composable
fun SSegmentedButtonScreenPreview() {
    val list = listOf("a", "b ", "c")
    SegmentedButton(list, 1, {}) {}
}