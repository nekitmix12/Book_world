package nekit.corporation.home_nav

import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nekit.corporation.bookmarks.BookmarksUi
import nekit.corporation.common_ui.R.drawable.bookmarks
import nekit.corporation.common_ui.R.drawable.play
import nekit.corporation.library.LibraryUi
import nekit.corporation.search.R.drawable.find
import nekit.corporation.search.SearchUi

@Composable
fun HomeUi(component: HomeComponent) {
    val state by component.childStack.subscribeAsState()

    Box() {
        Children(state) {
            when (val instance = it.instance) {
                is HomeComponent.BottomTabComponent.LibraryChild -> LibraryUi(instance.component)
                is HomeComponent.BottomTabComponent.SearchChild -> SearchUi(instance.component)
                is HomeComponent.BottomTabComponent.BookmarksChild -> BookmarksUi(instance.component)
            }
        }
        BottomBar(
            onSelect = component::onTabSelected,
            onPlayClick = component::onPlay,
            onOutClick = component::onOut
        )
    }

}

@Composable
fun BottomBar(
    onSelect: (HomeComponent.BottomTab) -> Unit,
    onPlayClick: () -> Unit,
    onOutClick: () -> Unit
) {
    var selected by remember { mutableStateOf<HomeComponent.BottomTab>(HomeComponent.BottomTab.LibraryChild) }
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
                .background(nekit.corporation.common_ui.theme.accent_dark)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onSelect(HomeComponent.BottomTab.LibraryChild)
                    selected = HomeComponent.BottomTab.LibraryChild
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("library")
            ) {
                Icon(
                    painterResource(R.drawable.bookshelf),
                    "",
                    tint = if (selected is HomeComponent.BottomTab.LibraryChild) nekit.corporation.common_ui.theme.white else nekit.corporation.common_ui.theme.accent_medium,
                )
            }
            IconButton(
                onClick = {
                    onSelect(HomeComponent.BottomTab.SearchChild)
                    selected = HomeComponent.BottomTab.SearchChild
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("search")
            ) {
                Icon(
                    painter = painterResource(id = find),
                    contentDescription = "",
                    tint = if (selected is HomeComponent.BottomTab.SearchChild) nekit.corporation.common_ui.theme.white else nekit.corporation.common_ui.theme.accent_medium,
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    onSelect(HomeComponent.BottomTab.BookmarksChild)
                    selected = HomeComponent.BottomTab.BookmarksChild
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("markup")
            ) {
                Icon(
                    painter = painterResource(bookmarks),
                    contentDescription = "",
                    tint = if (selected is HomeComponent.BottomTab.BookmarksChild) nekit.corporation.common_ui.theme.white else nekit.corporation.common_ui.theme.accent_medium,
                )
            }
            IconButton(
                onClick = onOutClick,
                modifier = Modifier
                    .weight(1f)
                    .testTag("out")
            ) {
                Icon(
                    painterResource(R.drawable.out),
                    "",
                    tint = nekit.corporation.common_ui.theme.accent_medium
                )
            }
        }
        IconButton(
            onClick = { onPlayClick() },
            modifier = Modifier
                .clip(CircleShape)
                .background(nekit.corporation.common_ui.theme.secondary)
                .padding(16.dp)
                .testTag("play")
        ) {
            Icon(
                painterResource(play),
                "",
                Modifier,
                nekit.corporation.common_ui.theme.white
            )
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
