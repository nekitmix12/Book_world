package nekit.corporation.details.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nekit.corporation.common_ui.R.drawable
import nekit.corporation.common_ui.R.drawable.bookmarks
import nekit.corporation.details.R
import nekit.corporation.details.models.ChapterState
import nekit.corporation.details.models.ShortChapterModel

//@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun BookDetailUi(
    component: DetailsComponent
) {
    val state by component.state.collectAsState()



    LazyColumn(
        Modifier
            .background(nekit.corporation.common_ui.theme.background)
            .fillMaxSize()
            .testTag("Book details")
    ) {
        item { DetailsImage(state.image!!, component::onBackClick) }
        item {
            ButtonRow(
                component::onReadClick, component::onAddToFavoriteClick
            )
        }
        item { DetailsName("Код да винчи") }
        item { CommonDetailsText("Дэн Браун") }
        item { Spacer(Modifier.height(24.dp)) }
        item {
            CommonDetailsText(
                "Секретный код скрыт в работах Леонардо да Винчи...\n" + "Только он поможет найти христианские святыни, дающие немыслимые власть и могущество... \n" + "Ключ к величайшей тайне, над которой человечество билось веками, наконец может быть найден..."
            )
        }
        if (state.chapters.find { it.state is ChapterState.Passed } != null) {
            item { Spacer(Modifier.height(16.dp)) }
            item { nekit.corporation.common_ui.ui_kit.MiddleLabel(stringResource(R.string.was_read)) }
            item {
                /* ProgressReadBar(
                     0.1f,
                     Modifier
                         .padding(top = 12.dp)
                         .padding(horizontal = 16.dp)
                 )*/
            }
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { nekit.corporation.common_ui.ui_kit.MiddleLabel(stringResource(R.string.toc)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(state.chapters.size) {
            ChapterItem(state.chapters[it], component::onChapterClick)
        }
    }
}

@Composable
fun DetailsImage(
    image: ImageBitmap,
    onBackClick: () -> Unit,
) {
    Box() {
        Image(
            bitmap = image,
            "",
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .testTag("detail image"),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color.Transparent,
                        1f to nekit.corporation.common_ui.theme.background
                    )
                )
        )
        Box(
            Modifier
                .padding(start = 20.dp)
                .padding(top = (LocalConfiguration.current.screenHeightDp * 0.076).dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(nekit.corporation.common_ui.theme.accent_dark),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onBackClick, modifier = Modifier.testTag("back from details")) {
                Icon(
                    painter = painterResource(drawable.back),
                    "",
                    tint = nekit.corporation.common_ui.theme.white
                )
            }
        }
    }

}


@Composable
fun ButtonRow(onReadClick: () -> Unit, onAddToFavouriteClick: () -> Unit) {
    Row(Modifier.padding(horizontal = 16.dp)) {
        Button(
            onClick = onReadClick,
            modifier = Modifier
                .weight(1f)
                .offset(y = ((-24).dp))
                .testTag("read button"),
            colors = ButtonColors(
                containerColor = nekit.corporation.common_ui.theme.accent_dark,
                contentColor = nekit.corporation.common_ui.theme.white,
                disabledContainerColor = nekit.corporation.common_ui.theme.accent_dark,
                disabledContentColor = nekit.corporation.common_ui.theme.white,
            )
        ) {
            Icon(painter = painterResource(drawable.play), "", Modifier.padding(vertical = 5.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.read),
                style = nekit.corporation.common_ui.theme.detailsBodyButton
            )
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onAddToFavouriteClick,
            modifier = Modifier
                .weight(1f)
                .offset(y = ((-24).dp))
                .testTag("toFavorite button"),
            colors = ButtonColors(
                containerColor = nekit.corporation.common_ui.theme.accent_light,
                contentColor = nekit.corporation.common_ui.theme.accent_dark,
                disabledContainerColor = nekit.corporation.common_ui.theme.accent_light,
                disabledContentColor = nekit.corporation.common_ui.theme.accent_dark,
            )
        ) {
            Icon(
                painter = painterResource(bookmarks),
                "",
                Modifier.padding(vertical = 5.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.in_favorite),
                style = nekit.corporation.common_ui.theme.detailsBodyButton
            )
        }
    }
}

@Composable
fun DetailsName(name: String) {
    Text(
        text = name.uppercase(),
        style = nekit.corporation.common_ui.theme.detailsLabel,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .testTag("details name")
    )
}

@Composable
fun CommonDetailsText(text: String) {
    val paragraphs = text.split("\n")

    Column(Modifier.testTag("CommonDetailsText")) {
        paragraphs.forEach { paragraph ->
            Text(
                text = paragraph,
                style = nekit.corporation.common_ui.theme.detailsBody,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ChapterItem(
    chapter: ShortChapterModel,
    onClick: (String) -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick(chapter.id) }
            .testTag("chapters")) {
        when (chapter.state) {
            is ChapterState.Passed -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = nekit.corporation.common_ui.theme.detailsBody
                )

                Icon(
                    painter = painterResource(R.drawable.access),
                    "",
                    tint = nekit.corporation.common_ui.theme.accent_medium,
                    modifier = Modifier.padding(vertical = 13.5.dp),
                )


            }

            is ChapterState.InProgress -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = nekit.corporation.common_ui.theme.detailsSelectedBody
                )
                Icon(
                    painter = painterResource(R.drawable.any),
                    "",
                    tint = nekit.corporation.common_ui.theme.accent_dark,
                    modifier = Modifier
                        .padding(vertical = 13.5.dp)
                        .clickable { onClick(chapter.id) },
                )
            }

            is ChapterState.NotWatch -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = nekit.corporation.common_ui.theme.detailsBody
                )
            }

            else -> {}
        }

    }
}