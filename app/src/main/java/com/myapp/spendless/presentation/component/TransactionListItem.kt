package com.myapp.spendless.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.spendless.R
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.ui.theme.Error
import com.myapp.spendless.ui.theme.PrimaryContainer
import com.myapp.spendless.ui.theme.PrimaryFixed
import com.myapp.spendless.ui.theme.Success
import com.myapp.spendless.ui.theme.SurfaceBackground
import java.text.DecimalFormat

@Composable
fun TransactionListItem(transaction: Transaction) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceBackground, RoundedCornerShape(16.dp))
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(PrimaryFixed, RoundedCornerShape(12.dp))
                    .width(44.dp)
                    .height(44.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(transaction.icon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier
                .weight(5f)
                .padding(4.dp)) {
                Text(text = transaction.title, fontSize = 16.sp)
                Text(text = transaction.category, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))

            val amountText = if(transaction.category == "Income"){
                transaction.amount.toString().toIncomeUnit()
            }else{
                transaction.amount.toString().toExpensesUnit(Error)
            }
            Text(
                text = amountText,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.fig_tree_medium)),
                modifier = Modifier
                    .padding(4.dp)
                    //.weight(2f)
            )

        }
    }
}

fun String.toIncomeUnit(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = Success)){
            append("$")
        }
        append(this@toIncomeUnit)
    }
}


fun String.toExpensesUnit(color: Color): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)){
            append(" -$")
        }
        append(this@toExpensesUnit)
    }
}

fun String.toDollar(): String{
    return "$ $this"
}

fun formatAmount(amount: Double): String{
    val formatted = DecimalFormat("#,###.00")
    return formatted.format(amount)

}


@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    val cat = Transaction(
        id = 1,
        title = "Decoration",
        amount = "2000.0".toDouble(),
        note = "Home decoration expenses",
        icon = R.drawable.home,
        category = "Home",
        date = 1
    )
    TransactionListItem(cat)
}
