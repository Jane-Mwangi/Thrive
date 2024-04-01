package design.fiti.thrive.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import design.fiti.second_hand_app.presentation.screens.settings.SettingsScreen
import design.fiti.thrive.R
import design.fiti.thrive.presentation.navigation.Routes
import design.fiti.thrive.presentation.utility.ExpenseDialogueBox
import design.fiti.thrive.presentation.utility.TabScreen


@Composable
fun HomeScreen(
    homeViewmodel: HomeViewModel
) {
    val brush = Brush.horizontalGradient(listOf(Color(0xffCCE0E0), Color(0xffE9D4D3)))
    val viewModel: HomeViewModel = hiltViewModel()
    val screenState by viewModel.uiState.collectAsState()
    var totalExpense: Int = 0
    var totalIncome: Int = 0
    var netIncome: Int = 0

    LaunchedEffect(key1 = true) {
        homeViewmodel.getAllMyExpenses()
        homeViewmodel.getAllMyIncomes()

        totalExpense =
            screenState.expense.sumOf { Math.round(it.amount.toDouble()).toInt() }
        totalIncome =
            screenState.income.sumOf { Math.round(it.amount.toDouble()).toInt() }

        netIncome = totalIncome - totalExpense
    }
    var showDialogue: Boolean by remember {
        mutableStateOf(false)
    }
    var dialogueString: String by remember {
        mutableStateOf("")
    }
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showDialogue) {
                ExpenseDialogueBox(
                    homeViewmodel = homeViewmodel,
                    value = dialogueString,
                    setShowDialog = { showDialogue = it },
                    setValue = { dialogueString = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(brush = brush)
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.55f)
                    .padding(horizontal = 10.dp)

            ) {
                Column {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row {
                            Box(
                                modifier = Modifier
                                    .clip(shape = CircleShape)
                                    .size(45.dp)
                                    .background(Color(0XFFE9E1E0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.wallet1),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Net Balance")
                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(text = "$ $netIncome")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(30.dp)
                                .background(Color(0XFFE9E1E0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Spacer(
                            modifier = Modifier
                                .height(30.dp)
                        )
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(8.dp))
                                .weight(0.4f)
                                .height(70.dp)
                                .background(Color(0xFFF1E5E4)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(text = "Income")
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "$ $totalIncome")
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .width(4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(8.dp))
                                .weight(0.4f)
                                .height(70.dp)
                                .background(Color(0xFFF1E5E4)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(text = "Expense")
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "$ $totalExpense")
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                    )
                    Row {

                        //income button
                        Button(
                            onClick = {
                                showDialogue = true
                                viewModel.updateUserSelectionOption(transactionType = TransactionType.Income)
                                viewModel.getAllMyIncomes()

                            }, shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .weight(0.4f),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(30.dp))
                                    .height(50.dp)
                                    .weight(0.4f)
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Press this to open"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Add Income")
                                }
                            }

                        }

                        Spacer(
                            modifier = Modifier
                                .width(12.dp)
                        )

                        //expense button
                        Button(
                            onClick = {
                                showDialogue = true
                                viewModel.updateUserSelectionOption(transactionType = TransactionType.Expense)
                                viewModel.getAllMyExpenses()
                            },
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .weight(0.4f),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Press this to open"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Add Expense")
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
            TabScreen(homeViewmodel = homeViewmodel)
        }
    }
}

@Composable
fun BottomNavGraph(homeViewmodel: HomeViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreens.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavScreens.HomeScreen.route) {
                HomeScreen(homeViewmodel = homeViewmodel)
            }
            composable(route = BottomNavScreens.InsightScreen.route) {
                InsightScreen(
                    homeViewModel = homeViewmodel
                )
            }
            composable(route = BottomNavScreens.SettingsScreen.route) {
                SettingsScreen(homeViewModel = homeViewmodel) {
//                    handleSignOut()
                    navController.popBackStack()
                    //coz they've logged out
                    navController.navigate(Routes.ORIENTATION.name)

                }
            }


        }
    }

}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.safeDrawingPadding()
    ) {

        BottomNavigationItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.home1),
                tint = if (selectedIndex.value == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ),
                contentDescription = ""
            )
        },
            label = {
                Text(
                    text = "Home",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(600),
                        color = if (selectedIndex.value == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        textAlign = TextAlign.Center,
                    )
                )
            },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
                navHostController.navigate(BottomNavScreens.HomeScreen.route)
            })

        BottomNavigationItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.graphs),
                tint = if (selectedIndex.value == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ),
                contentDescription = "",
                modifier = Modifier.size(24.dp)

            )
        },
            label = {
                Text(
                    text = "Insights",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(600),
                        color = if (selectedIndex.value == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        textAlign = TextAlign.Center,
                    )
                )
            },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
                navHostController.navigate(BottomNavScreens.InsightScreen.route)
            })

        BottomNavigationItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                tint = if (selectedIndex.value == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ),
                contentDescription = ""
            )
        },
            label = {
                Text(
                    text = "Settings",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(600),
                        color = if (selectedIndex.value == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        textAlign = TextAlign.Center,
                    )
                )
            },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
                navHostController.navigate(BottomNavScreens.SettingsScreen.route)
            })
    }
}

sealed class BottomNavScreens(val route: String) {
    object HomeScreen : BottomNavScreens(
        route = BottomNavRoute.Home.name
    )

    object InsightScreen : BottomNavScreens(
        route = BottomNavRoute.Insight.name
    )

    object SettingsScreen : BottomNavScreens(
        route = BottomNavRoute.Settings.name
    )
}


enum class BottomNavRoute {
    Home, Insight, Settings
}