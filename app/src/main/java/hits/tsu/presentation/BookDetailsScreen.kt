package hits.tsu.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hits.tsu.R
import hits.tsu.presentation.models.ChapterState
import hits.tsu.presentation.models.ShortChapterModel
import nekit.corporation.common.theme.accent_dark
import nekit.corporation.common.theme.accent_light
import nekit.corporation.common.theme.accent_medium
import nekit.corporation.common.theme.background
import nekit.corporation.common.theme.detailsBody
import nekit.corporation.common.theme.detailsBodyButton
import nekit.corporation.common.theme.detailsLabel
import nekit.corporation.common.theme.detailsSelectedBody
import nekit.corporation.common.theme.white
import java.util.UUID


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun BookDetailsScreen(
    navController: NavController = rememberNavController(),
    bookId: String = "null",
) {

    val chaptersList = listOf(
        ShortChapterModel(
            UUID.randomUUID().toString(), name = "Факты", ChapterState.Passed
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Пролог", ChapterState.InProgress
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 1", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 2", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 3", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 4", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 5", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 6", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 7", ChapterState.NotWatch
        ), ShortChapterModel(
            UUID.randomUUID().toString(), name = "Глава 8", ChapterState.NotWatch
        )
    )

    val onReadClick: (String) -> Unit =
        { if (it.isNotEmpty()) navController.navigate(Screens.Chapter(it)) }
    val onAddToFavoriteClick = {}
    val onBackClick: () -> Unit = {
        navController.popBackStack()
    }

    LazyColumn(
        Modifier
            .background(background)
            .fillMaxSize()
            .testTag("Book details")
    ) {
        item { DetailsImage(onBackClick = onBackClick) }
        item {
            ButtonRow({
                onReadClick(
                    chaptersList.find { it.state == ChapterState.InProgress }?.id ?: ""
                )
            }, onAddToFavoriteClick)
        }
        item { DetailsName("Код да винчи") }
        item { CommonDetailsText("Дэн Браун") }
        item { Spacer(Modifier.height(24.dp)) }
        item {
            CommonDetailsText(
                "Секретный код скрыт в работах Леонардо да Винчи...\n" + "Только он поможет найти христианские святыни, дающие немыслимые власть и могущество... \n" + "Ключ к величайшей тайне, над которой человечество билось веками, наконец может быть найден..."
            )
        }
        if (chaptersList.find { it.state is ChapterState.Passed } != null) {
            item { Spacer(Modifier.height(16.dp)) }
            item { MiddleLabel(stringResource(R.string.was_read)) }
            item {
                ProgressReadBar(
                    0.1f,
                    Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.toc)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(chaptersList.size) {
            ChapterItem(chaptersList[it]) { chapter -> onReadClick(chapter.id) }
        }
    }
}

@Composable
fun DetailsImage(
    image: ImageBitmap = ImageBitmap.imageResource(R.drawable.test_new_carousel),
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
                        0.0f to Color.Transparent, 1f to background
                    )
                )
        )
        Box(
            Modifier
                .padding(start = 20.dp)
                .padding(top = (LocalConfiguration.current.screenHeightDp * 0.076).dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(accent_dark),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onBackClick, modifier = Modifier.testTag("back from details")) {
                Icon(
                    painter = painterResource(R.drawable.back), "", tint = white
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
                .offset(y = ((-24).dp)).testTag("read button"),
            colors = ButtonColors(
                containerColor = accent_dark,
                contentColor = white,
                disabledContainerColor = accent_dark,
                disabledContentColor = white,
            )
        ) {
            Icon(painter = painterResource(R.drawable.play), "", Modifier.padding(vertical = 5.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = stringResource(R.string.read), style = detailsBodyButton)
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onAddToFavouriteClick,
            modifier = Modifier
                .weight(1f)
                .offset(y = ((-24).dp)).testTag("toFavorite button"),
            colors = ButtonColors(
                containerColor = accent_light,
                contentColor = accent_dark,
                disabledContainerColor = accent_light,
                disabledContentColor = accent_dark,
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.bookmarks),
                "",
                Modifier.padding(vertical = 5.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.in_favorite), style = detailsBodyButton
            )
        }
    }
}

@Composable
fun DetailsName(name: String) {
    Text(
        text = name.uppercase(),
        style = detailsLabel,
        modifier = Modifier.padding(horizontal = 16.dp).testTag("details name")
    )
}

@Composable
fun CommonDetailsText(text: String) {
    val paragraphs = text.split("\n")

    Column(Modifier.testTag("CommonDetailsText")) {
        paragraphs.forEach { paragraph ->
            Text(
                text = paragraph,
                style = detailsBody,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ChapterItem(
    chapter: ShortChapterModel,
    onClick: (ShortChapterModel) -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick(chapter) }
            .testTag("chapters")) {
        when (chapter.state) {
            is ChapterState.Passed -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = detailsBody
                )

                Icon(
                    painter = painterResource(R.drawable.access),
                    "",
                    tint = accent_medium,
                    modifier = Modifier.padding(vertical = 13.5.dp),
                )


            }

            is ChapterState.InProgress -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = detailsSelectedBody
                )
                Icon(
                    painter = painterResource(R.drawable.any),
                    "",
                    tint = accent_dark,
                    modifier = Modifier
                        .padding(vertical = 13.5.dp)
                        .clickable { onClick(chapter) },
                )
            }

            is ChapterState.NotWatch -> {
                Text(
                    text = chapter.name,
                    Modifier
                        .weight(1f)
                        .padding(vertical = 13.5.dp),
                    style = detailsBody
                )
            }

            else -> {}
        }

    }
}