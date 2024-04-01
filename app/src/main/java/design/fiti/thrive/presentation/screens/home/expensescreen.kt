package design.fiti.thrive.presentation.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import design.fiti.thrive.domain.model.Expense
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

data class ExpenseItem(
    val iconRes: Int,
    val title: String,
    val price: Double
)

@Composable
fun ExpenseScreen(
    homeViewmodel: HomeViewModel
) {

    val screenState by homeViewmodel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        homeViewmodel.getAllMyExpenses()
    }
    // Sample list of expense items
    val expenseItems = listOf(
        ExpenseItem(R.drawable.foodicon, "Expense 1", 100.0),
        ExpenseItem(R.drawable.foodicon, "Expense 2", 150.0),
        ExpenseItem(R.drawable.foodicon, "Expense 3", 200.0),
        // Add more items as needed
    )

    LazyColumn {
        if (screenState.expense.isNullOrEmpty()) {
            when (screenState.loadState) {
                is WhenToNavigate.Processing -> {
                    item {
                        Column {
                            Spacer(modifier = Modifier.height(32.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }


                        }
                    }
                }

                is WhenToNavigate.Stopped -> {
                    item {
                        Text(text = "Something went wrong...")
                    }
                }

                is WhenToNavigate.Go -> {
                    item {
                        Text(text = "You'll need to add new expenses to see them here.")
                    }
                }
            }

        } else {
            items(screenState.expense) { item ->
                ExpenseItemRow(item = item)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ExpenseItemRow(item: Expense) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(25.dp)
                )
                .size(52.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.tertiary,
                painter = painterResource(id = R.drawable.expense),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)


            )

        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = item.name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "$${item.amount}")

    }
}
