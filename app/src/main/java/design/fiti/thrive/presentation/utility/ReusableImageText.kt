package design.fiti.thrive.presentation.utility

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.fiti.thrive.R


@Composable
fun ReusableImageWithText(
    imageResourceId: Int,
    welcomeText: String
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow),
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier.padding(top = 40.dp, start = 50.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.thrive_logo),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = welcomeText,
                        modifier = Modifier.padding(),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}

