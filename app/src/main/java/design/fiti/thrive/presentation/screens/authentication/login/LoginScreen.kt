package design.fiti.thrive.presentation.screens.authentication.login


import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import design.fiti.thrive.R
import design.fiti.thrive.presentation.navigation.UpdatePreferencesViewModel
import design.fiti.thrive.presentation.screens.authentication.signup.SignUpViewModel
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

import design.fiti.thrive.presentation.utility.ReusableAppButton


@Preview
@Composable
fun SignInScreen(
    navigateToHomeScreen: () -> Unit = {},
    navController: NavHostController,
    preferencesViewModel: UpdatePreferencesViewModel
) {
    Scaffold { innerPadding ->
        val viewModel: SignUpViewModel = hiltViewModel()
        val screenState by viewModel.uiState.collectAsState()
        var signInprogress by rememberSaveable {
            mutableStateOf(false)
        }
        val scrollState = rememberScrollState()

        LaunchedEffect(key1 = screenState.isLoading) {
            when (screenState.isLoading) {
                is WhenToNavigate.Stopped -> {
                    signInprogress = false
                }

                is WhenToNavigate.Processing -> {
                    signInprogress = true
                }

                is WhenToNavigate.Go -> {
                    screenState.apiResult?.let {
                        preferencesViewModel.updateUserAuthTokenPref(
                            authToken = it
                        )
                    }
                    Log.d(
                        "Thrive Back Navigation",
                        "SignInScreen: stored token indeed......................${preferencesViewModel.getDeviceTokenPref()}"
                    )
                    navigateToHomeScreen()
                    signInprogress = false
                }
            }

        }
        Column(
            modifier = Modifier.verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(innerPadding)) {
                Image(
                    painter = painterResource(id = R.drawable.auth_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    contentScale = ContentScale.FillWidth
                )
                Box(modifier = Modifier.padding(top = 40.dp, start = 50.dp)) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.thrive_logo),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sign In to continue",
                            modifier = Modifier.padding(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            if (signInprogress) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Signing you in...")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            EmailTextField(
                text = screenState.email,
                onInputValueChange = { newTextValue -> viewModel.updateEmailInput(newTextValue) })
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextField(
                text = screenState.password,
                onInputValueChange = { newTextValue -> viewModel.updatePasswordInput(newTextValue) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            ReusableAppButton(text = "Sign in", onClick = {
                Log.d("Sign In", "Signing you In")
                signInprogress = true
                viewModel.LogIn()
            })

        }
    }
}

@Composable
fun EmailTextField(text: String, onInputValueChange: (String) -> Unit) {


    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email, contentDescription = "passwordIcon"
            )
        },

        onValueChange = {
            onInputValueChange(it)
        },
        label = { Text(text = "Enter Email") },
        placeholder = { Text(text = "Confirm your password") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)


    )
}

@Composable
fun PasswordTextField(text: String, onInputValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
            )
        },
        onValueChange = {
            onInputValueChange(it)
        },
        label = { Text(text = "Password", color = MaterialTheme.colorScheme.onBackground) },
        placeholder = { Text(text = "Enter your password", color = Color.Gray) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )

}

