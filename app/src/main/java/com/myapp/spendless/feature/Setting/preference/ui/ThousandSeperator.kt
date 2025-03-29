package com.myapp.spendless.feature.Setting.preference.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
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
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.SegmentedAppButton
import com.myapp.spendless.feature.Setting.preference.model.ThousandSeparator

@Composable
fun ThousandSeperatorBox(onClick: (ThousandSeparator) -> Unit) {

    var selectedIndexForFormat by remember { mutableIntStateOf(0) }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Thousands seperator",
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        SegmentedAppButton(
            list = listOf("1,000", "1.000", "1 000"),
            selectedIndex = selectedIndexForFormat,
            { selectedIndexForFormat = it }) { indexThousands ->
            when (indexThousands) {
                0 -> onClick(ThousandSeparator.Comma)
                1 -> onClick(ThousandSeparator.Dot)
                2 -> onClick(ThousandSeparator.Space)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThousandFormatPreview() {
  ThousandSeperatorBox {  }
}
