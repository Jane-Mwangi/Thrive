package design.fiti.thrive.presentation.screens.orientation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.fiti.thrive.R
import design.fiti.thrive.presentation.utility.ReusableAppButton


@Preview
@Composable
fun OrientationScreen(
    navigateToSignUpScreen: () -> Unit = {},
    navigateToSignInScreen: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(state = scrollState)) {

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
                            text = "Welcome", modifier = Modifier.padding(), style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp
                            )
                        )
                    }
                }
            }
            Text(
                text = "Create an account to get started",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "OR",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(60.dp))
            ReusableAppButton(text = "Sign Up", onClick = {
                navigateToSignUpScreen()
            })
            Spacer(modifier = Modifier.height(16.dp))
            ReusableAppButton(text = "Sign In", onClick = {
                navigateToSignInScreen()
            })
        }
    }
}