package design.fiti.thrive.presentation.screens.home


import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import design.fiti.thrive.domain.model.Income
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

data class IncomeItem(
    val iconRes: Int,
    val title: String,
    val price: Double
)

@Composable
fun IncomeScreen(
    homeViewmodel: HomeViewModel
) {

    val screenState by homeViewmodel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        homeViewmodel.getAllMyIncomes()
    }

    // Sample list of income items
    val incomeItems = listOf(
        IncomeItem(R.drawable.foodicon, "Item 1", 100.0),
        IncomeItem(R.drawable.foodicon, "Item 2", 150.0),
        IncomeItem(R.drawable.foodicon, "Item 3", 200.0),
        // Add more items as needed
    )

    Log.d("UI STuff", "data got back is: ${screenState.income}")
    LazyColumn {
        if (screenState.income.isNullOrEmpty()) {
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
                        Text(text = "You'll need to add new incomes to see them here.")
                    }
                }
            }

        } else {
            items(screenState.income) { item ->
                IncomeItemRow(item = item)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun IncomeItemRow(item: Income) {


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
                painter = painterResource(id = R.drawable.income),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .padding(end = 16.dp)

            )

        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = item.name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "$${item.amount}")

    }
}
