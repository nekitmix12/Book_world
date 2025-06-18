package nekit.corporation.details.chapter.side_sheet

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.detailsBody
import nekit.corporation.common_ui.theme.detailsSelectedBody
import nekit.corporation.common_ui.theme.labelText
import nekit.corporation.details.R
import nekit.corporation.common_ui.R.drawable
import nekit.corporation.details.models.ChapterState

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
@Stable
fun ChapterSideSheet(
    component: SideSheetComponent
) {
    val state by component.state.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val offsetX by animateFloatAsState(
        targetValue = if (state.isVisible) 0f else screenWidth.toFloat(),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "BoxAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = component::onDismiss)
    ) {
        Box(
            modifier = Modifier
                .offset(x = offsetX.dp)
                .fillMaxSize()
                .padding(top = 52.dp, bottom = 24.dp, start = 92.dp)
                .shadow(
                    5.dp, shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 0.dp
                    )
                )
                .background(nekit.corporation.common_ui.theme.background)
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                item() {
                    Row(
                        Modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.toc),
                            style = nekit.corporation.common_ui.theme.labelText,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            painterResource(drawable.close),
                            "",
                            tint = nekit.corporation.common_ui.theme.accent_dark,
                            modifier = Modifier
                                .padding(17.dp)
                                .padding(8.dp)
                                .padding(vertical = 16.dp)
                                .clickable(onClick = component::onDismiss)
                        )
                    }
                }
                items(state.chapters) {
                    Text(
                        text = it.name,
                        style = if (it.state != ChapterState.InProgress) nekit.corporation.common_ui.theme.detailsBody
                        else nekit.corporation.common_ui.theme.detailsSelectedBody,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 13.5.dp)
                            .clickable { component.onChapterClick(it.id) }
                    )
                }
            }
        }
    }
}