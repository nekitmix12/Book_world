@file:Suppress("DEPRECATION")

package hits.tsu.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hits.tsu.R
import hits.tsu.presentation.theme.BookWorldTheme
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.secondary
import hits.tsu.presentation.theme.white
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookWorldTheme {
                Nav()
            }
        }
    }
}

@Composable
fun CustomSystemBarsScreen(statusBarColor: Color) {
    val context = LocalContext.current
    val activity = context as? Activity

    SideEffect {
        activity?.window?.let { window ->
            window.statusBarColor = statusBarColor.toArgb()
        }
    }

}

@Composable
fun Nav() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast('.')
    val bottomBarRoutes = listOf(
        Screens.Library::class.simpleName,
        Screens.Search::class.simpleName,
        Screens.Bookmarks::class.simpleName
    )
    Box() {
        CustomSystemBarsScreen(Color.Transparent)
        NavHost(
            navController = navController,
            startDestination = Screens.SignIn,
        ) {
            composable<Screens.SignIn> {
                SignInScreen(navController)
            }
            composable<Screens.Library> {
                LibraryScreen(navController)
            }
            composable<Screens.BookDetails> {
                val args = it.toRoute<Screens.BookDetails>()
                BookDetailsScreen(navController, args.bookId)
            }
            composable<Screens.Chapter> {
                val args = it.toRoute<Screens.Chapter>()
                ChapterScreen(navController, args.chapterId)
            }
            composable<Screens.Bookmarks> {
                BookmarksScreen(navController)
            }
            composable<Screens.Search> {
                SearchScreen(navController)
            }

        }
        if (currentRoute in bottomBarRoutes) {
            Box(Modifier.align(Alignment.BottomCenter)) {
                BottomBar(navController)
            }
        }
    }


}

@Preview(showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun BottomBar(
    navController: NavController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast('.')

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 40.dp)
            .background(
                Color.Transparent
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp)
                .clip(CircleShape)
                .background(accent_dark)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate(Screens.Library) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painterResource(R.drawable.bookshelf),
                    "",
                    tint = if (currentRoute == Screens.Library::class.simpleName) white else accent_medium,
                )
            }
            IconButton(
                onClick = { navController.navigate(Screens.Search) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.find),
                    contentDescription = "",
                    tint = if (currentRoute == Screens.Search::class.simpleName) white else accent_medium,
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = { navController.navigate(Screens.Bookmarks) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bookmarks),
                    contentDescription = "",
                    tint = if (currentRoute == Screens.Bookmarks::class.simpleName) white else accent_medium,
                )
            }
            IconButton(
                onClick = { navController.navigate(Screens.SignIn) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painterResource(R.drawable.out),
                    "",
                    tint = accent_medium
                )
            }
        }
        IconButton(
            onClick = { navController.navigate(Screens.Chapter(UUID.randomUUID().toString())) },
            modifier = Modifier
                .clip(CircleShape)
                .background(secondary)
                .padding(16.dp)
                .clickable {
                    navController.navigate(Screens.Chapter("${UUID.randomUUID()}"))
                }
        ) {
            Icon(
                painterResource(R.drawable.play),
                "",
                Modifier,
                white
            )
        }
    }
}

