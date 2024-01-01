package design.fiti.thrive.presentation.screens.authentication.signup


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.fiti.thrive.R

import design.fiti.thrive.presentation.utility.ReusableAppButton


@Preview
@Composable
fun SignUPScreen(
    navigateToSignInScreen: () -> Unit = {}
) {
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
                            text = "Sign Up to continue",
                            modifier = Modifier.padding(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            EmailTextField()
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(16.dp))
            ConfirmPasswordTextField()
            Spacer(modifier = Modifier.height(16.dp))
            ReusableAppButton(text = "Sign Up", onClick = {
                navigateToSignInScreen()
            })

        }
    }
}

@Composable
fun EmailTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email, contentDescription = "passwordIcon"
            )
        },

        onValueChange = {
            text = it
        },
        label = { Text(text = "Enter Email") },
        placeholder = { Text(text = "Confirm your password") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(285.dp)
            .height(50.dp)


    )
}

@Composable
fun PasswordTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
            )
        },
        onValueChange = {
            text = it
        },
        label = { Text(text = "Password", color = Color.Black) },
        placeholder = { Text(text = "Enter your password", color = Color.Gray) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(285.dp)
            .height(50.dp)
    )

}

@Composable
fun ConfirmPasswordTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock, contentDescription = "emailIcon"
            )
        },
        onValueChange = {
            text = it
        },
        label = { Text(text = "Confirm Password", color = Color.Black) },
        placeholder = { Text(text = "Confirm your password", color = Color.Gray) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(285.dp)
            .height(50.dp)


    )

}
