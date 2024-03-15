package design.fiti.thrive.presentation.screens.home


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import design.fiti.thrive.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

data class ExpenseItem(
    val iconRes: Int,
    val title: String,
    val price: Double
)

@Composable
fun ExpenseScreen() {
    // Sample list of income items
    val expenseItems = listOf(
        ExpenseItem(R.drawable.foodicon, "Expense 1", 100.0),
        ExpenseItem(R.drawable.foodicon, "Expense 2", 150.0),
        ExpenseItem(R.drawable.foodicon, "Expense 3", 200.0),
        // Add more items as needed
    )

    LazyColumn {
        items(expenseItems) { item ->
            ExpenseItemRow(item = item)
        }
    }
}

@Composable
fun ExpenseItemRow(item: ExpenseItem) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {

        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .padding(end = 16.dp)
        )

        Text(text = item.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "$${item.price}")

    }
}
