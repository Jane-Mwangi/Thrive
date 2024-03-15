package design.fiti.thrive.presentation.screens.authentication.signup


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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import design.fiti.thrive.R

import design.fiti.thrive.presentation.utility.ReusableAppButton


@Preview
@Composable
fun SignUPScreen(
    navigateToSignInScreen: () -> Unit = {}
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val screenState by viewModel.uiState.collectAsState()
    var signupInprogress by rememberSaveable {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = screenState.isLoading) {
        when (screenState.isLoading) {
            is WhenToNavigate.Stopped -> {
                signupInprogress = false
            }

            is WhenToNavigate.Processing -> {
                signupInprogress = true
            }

            is WhenToNavigate.Go -> {
                navigateToSignInScreen()
                signupInprogress = false
            }
        }

    }

    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()
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
                            text = "Sign up to continue",
                            modifier = Modifier.padding(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp
                            )
                        )
                    }
                }
            }
            if (signupInprogress) {
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
                            Text(text = "Signing you up...")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            EmailTextField(
                text = screenState.email, handleInputChange = { textInput ->
                    viewModel.updateEmailInput(textInput)
                }, error = screenState.emailError
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField(
                text = screenState.password, handleInputChange = { textInput ->
                    viewModel.updatePasswordInput(textInput)
                }, error = screenState.passwordError
            )
            Spacer(modifier = Modifier.height(16.dp))
            ConfirmPasswordTextField(
                text = screenState.confirmPassword, handleInputChange = { textInput ->
                    viewModel.updateConfirmPasswordInput(textInput)
                }, error = screenState.confirmPasswordError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = screenState.apiResult ?: "", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))
            ReusableAppButton(text = "Sign Up", onClick = {

                Log.d("Sign Up", "Signing you up")
                signupInprogress = true
                viewModel.signUp()
//                navigateToSignInScreen()
            })

        }
    }
}

@Composable
fun EmailTextField(
    text: String, handleInputChange: (String) -> Unit, error: String
) {
    Column {
        OutlinedTextField(
            value = text,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email, contentDescription = "passwordIcon"
                )
            },

            onValueChange = {
                handleInputChange(it)
            },
            label = { Text(text = "Enter Email") },
            placeholder = { Text(text = stringResource(id = R.string.email_placeholder_text)) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)


        )
        Text(text = error)

    }

}

@Composable
fun PasswordTextField(text: String, handleInputChange: (String) -> Unit, error: String) {

    Column {

        OutlinedTextField(
            value = text,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
                )
            },
            onValueChange = {
                handleInputChange(it)
            },
            label = { Text(text = "Password", color = MaterialTheme.colorScheme.onBackground) },
            placeholder = { Text(text = "Enter your password", color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
        Text(text = error)
    }

}

@Composable
fun ReusablePasswordTextField(
    text: String,
    label: String,
    placeHolder: String,
    handleInputChange: (String) -> Unit,
    error: String
) {

    Column {

        OutlinedTextField(
            value = text,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
                )
            },
            onValueChange = {
                handleInputChange(it)
            },
            label = { Text(text = label, color = MaterialTheme.colorScheme.onBackground) },
            placeholder = { Text(text = placeHolder, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
        Text(text = error)
    }

}

@Composable
fun ConfirmPasswordTextField(text: String, handleInputChange: (String) -> Unit, error: String) {
    Column {
        OutlinedTextField(
            value = text,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
                )
            },
            onValueChange = {
                handleInputChange(it)
            },
            label = {
                Text(
                    text = "Confirm Password",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            placeholder = { Text(text = "Confirm your password", color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)


        )
        Text(text = error)

    }


}
