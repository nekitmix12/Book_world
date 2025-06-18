package nekit.corporation.details.chapter.toolbar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import nekit.corporation.common.R.drawable
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.white
import nekit.corporation.details.R

@Composable
fun ChapterBottomBar(
    component: ToolbarComponent
) {
    val state by component.state.collectAsState()

    BottomAppBar(
        actions = {
            IconButton(onClick = component::onPrevClick) {
                Icon(
                    painterResource(R.drawable.slide_back),
                    contentDescription = "Localized description",
                    tint = nekit.corporation.common_ui.theme.white
                )
            }
            IconButton(onClick = component::onChaptersClick) {
                Icon(
                    painterResource(R.drawable.more),
                    contentDescription = "Localized description",
                    tint = nekit.corporation.common_ui.theme.white
                )
            }
            IconButton(onClick = component::onNextClick) {
                Icon(
                    painterResource(R.drawable.next),
                    contentDescription = "Localized description",
                    tint = nekit.corporation.common_ui.theme.white
                )
            }
            IconButton(onClick = component::onSettingsClick) {
                Icon(
                    painterResource(R.drawable.settings),
                    contentDescription = "Localized description",
                    tint = nekit.corporation.common_ui.theme.white
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = component::onSettingsClick,
                containerColor = nekit.corporation.common_ui.theme.accent_light,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {

                Icon(
                    painter = painterResource(if (state.isPlay) R.drawable.stop else drawable.play),
                    "",
                    tint = nekit.corporation.common_ui.theme.accent_dark
                )
            }
        },
        containerColor = nekit.corporation.common_ui.theme.accent_dark,
    )
}
