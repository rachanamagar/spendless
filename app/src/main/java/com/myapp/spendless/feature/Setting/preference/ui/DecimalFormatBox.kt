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
import com.myapp.spendless.feature.Setting.SegmentedButton
import com.myapp.spendless.feature.Setting.preference.model.DecimalSeparator


@Composable
fun DecimalFormatBox(onClick: (DecimalSeparator) -> Unit) {
    var selectedIndexForFormat by remember { mutableIntStateOf(0) }

    Column {
        Text(
            text = "Decimal seperator",
            fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        SegmentedButton(
            list = listOf("1.00", "1,00"),
            selectedIndex = selectedIndexForFormat,
            onSelectedIndex = { selectedIndexForFormat = it },
            onItemClicked = { indexDecimal ->
                when (indexDecimal) {
                    0 -> onClick(DecimalSeparator.Comma)
                    1 -> onClick(DecimalSeparator.Dot)
                }
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DecimalFormatPreview() {
    DecimalFormatBox { }
}
