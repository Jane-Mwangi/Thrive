package design.fiti.thrive.presentation.utility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import design.fiti.thrive.presentation.screens.home.ExpenseScreen
import design.fiti.thrive.presentation.screens.home.HomeViewModel
import design.fiti.thrive.presentation.screens.home.IncomeScreen

@Composable
fun TabScreen(homeViewmodel: HomeViewModel) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Income", "Expense")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }
        when (tabIndex) {
            0 -> IncomeScreen(homeViewmodel = homeViewmodel)
            1 -> ExpenseScreen(homeViewmodel=homeViewmodel)

        }
    }
}