package com.myapp.spendless.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myapp.spendless.R


@Composable
fun SegmentedButton(selectedIndex: Int, modifier: Modifier = Modifier, onSelectedIndex: (Int)-> Unit) {
    val options = listOf(
        "Expenses" to R.drawable.down,
        "Income" to R.drawable.up
    )

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, (label, icon) ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onSelectedIndex(index) },
                selected = index == selectedIndex,
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = label,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(label)
                    }
                }
            )
        }
    }
}