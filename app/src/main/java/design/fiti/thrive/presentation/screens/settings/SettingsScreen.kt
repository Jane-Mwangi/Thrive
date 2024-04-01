package design.fiti.second_hand_app.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import design.fiti.thrive.R
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate
import design.fiti.thrive.presentation.screens.home.HomeViewModel
import design.fiti.thrive.presentation.screens.settings.SettingsViewModel

@Composable
fun SettingsScreen(homeViewModel: HomeViewModel, handleLogOut: () -> Unit) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val screenState by viewModel.uiState.collectAsState()
    val homeVmState by homeViewModel.uiState.collectAsState()
    var isDeleteIconClicked by rememberSaveable {
        mutableStateOf(false)
    }
    var toBeDeleteProductId by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = screenState.loadState) {
        if (screenState.loadState is WhenToNavigate.Go) {
            handleLogOut()
        }
    }


    Scaffold { innerpadding ->
        Column(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(innerpadding)
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Settings",
                style = TextStyle(
                    fontSize = 11.79.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF615F5F),
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            LogOut {
                viewModel.handlePrefsCleanUp()
            }
            Spacer(modifier = Modifier.height(24.dp))
            DeleteAccount()
            Spacer(modifier = Modifier.height(24.dp))

        }

    }
}

//yagni - you arent gonna need it LOL
@Composable
fun MyPostsForSale() {

}

@Composable
fun LogOut(handleLogOut: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(0.75f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.logout),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(onClick = { handleLogOut() }) {
                Text(text = "Log Out", color = MaterialTheme.colorScheme.onBackground)
            }
        }
        Divider(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun DeleteAccount() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(0.75f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.width(16.dp))

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Delete Account", color = MaterialTheme.colorScheme.onBackground)
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,

    ) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            backgroundColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Are you sure you want to permanently delete this product from the market? Please note that this action is irreversible and once done cannot possibly be retrieved.",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.onBackground)
                    }
                    TextButton(
                        onClick = { onConfirmation() },

                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}