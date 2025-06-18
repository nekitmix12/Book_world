package nekit.corporation.common_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Alumni_Sans,
        fontSize = 96.sp,
        fontWeight = FontWeight(700),
        color = secondary,
        lineHeight = 76.8.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Alumni_Sans,
        fontSize = 48.sp,
        color = accent_light,
        fontWeight = FontWeight(700)
    ),

    bodySmall = TextStyle(
        fontFamily = Vela_Sans,
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        color = accent_medium,
        lineHeight = 18.2.sp
    )
)


