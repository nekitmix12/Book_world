package hits.tsu.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = secondary,
    tertiary = Pink40,
    onSecondary = accent_light

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun BookWorldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


val textBarText = TextStyle(
    color = accent_medium,
    fontWeight = FontWeight(400),
    fontFamily = Vela_Sans,
    fontSize = 16.sp,
    lineHeight = 20.8.sp
)

val bookNameSearch = TextStyle(
    color = accent_dark,
    fontWeight = FontWeight(700),
    fontFamily = Alumni_Sans,
    fontSize = 24.sp,
)

val bookAuthorSearch = TextStyle(
    color = accent_dark,
    fontWeight = FontWeight(400),
    fontFamily = Vela_Sans,
    fontSize = 14.sp,
    lineHeight = 18.2.sp
)

val labelText = TextStyle(
    color = accent_dark,
    fontWeight = FontWeight(700),
    fontFamily = Alumni_Sans,
    fontSize = 24.sp,
)

val requestText = TextStyle(
    color = accent_dark,
    fontWeight = FontWeight(400),
    fontFamily = Vela_Sans,
    fontSize = 14.sp,
)
val authorText = TextStyle(
    color = accent_dark,
    fontWeight = FontWeight(400),
    fontFamily = Vela_Sans,
    fontSize = 16.sp,
    lineHeight = 20.8.sp
)

