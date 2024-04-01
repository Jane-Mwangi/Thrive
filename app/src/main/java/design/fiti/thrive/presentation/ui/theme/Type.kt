
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import design.fiti.thrive.R


val NunitoFamily = FontFamily(
    //bold

    Font(R.font.nunito_bold, FontWeight.Bold),
    //extra light
    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
    //light
    Font(R.font.nunito_light, FontWeight.Light),
    //medium
    Font(R.font.nunito_medium, FontWeight.Medium),
    //regular
    Font(R.font.nunito_regular, FontWeight.Normal),
    //semi bold
    Font(R.font.nunito_semibold, FontWeight.SemiBold),


    )

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    //Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)