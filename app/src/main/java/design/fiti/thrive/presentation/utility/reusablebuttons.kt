package design.fiti.thrive.presentation.utility

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ReusableAppButton(
    text: String,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Cyan,
    roundedRadius: Dp = 8.dp,
    height: Dp = 46.dp,
    width: Dp = 285.dp, // Optional width parameter
    onClick: () -> Unit
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(roundedRadius),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = height)
                .width(width = width)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text,
                    color = textColor,
                    // Add your desired text style here, for example:
                    // style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

