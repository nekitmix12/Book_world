package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hits.tsu.R
import hits.tsu.presentation.models.ChapterModel
import hits.tsu.presentation.models.ChapterState
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_light
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.background
import hits.tsu.presentation.theme.detailsBody
import hits.tsu.presentation.theme.detailsBodyButton
import hits.tsu.presentation.theme.detailsLabel
import hits.tsu.presentation.theme.detailsSelectedBody
import hits.tsu.presentation.theme.white
import java.util.UUID


@Composable
fun BookDetailsScreen() {

    val chaptersList = listOf(
        ChapterModel(
            UUID.randomUUID().toString(), name = "Факты", ChapterState.Passed
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Пролог", ChapterState.InProgress
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 1", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 2", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 3", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 4", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 5", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 6", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 7", ChapterState.NotWatch
        ), ChapterModel(
            UUID.randomUUID().toString(), name = "Глава 8", ChapterState.NotWatch
        )
    )

    LazyColumn(
        Modifier
            .background(background)
            .fillMaxSize()
    ) {
        item { DetailsImage() }
        item { ButtonRow() }
        item { DetailsName("Код да винчи") }
        item { CommonDetailsText("Дэн Браун") }
        item { Spacer(Modifier.height(24.dp)) }
        item {
            CommonDetailsText(
                "Секретный код скрыт в работах Леонардо да Винчи...\n" + "Только он поможет найти христианские святыни, дающие немыслимые власть и могущество... \n" + "Ключ к величайшей тайне, над которой человечество билось веками, наконец может быть найден..."
            )
        }
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
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.toc)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(chaptersList.size) {
            ChapterItem(chaptersList[it])
        }
    }
}

@Composable
fun DetailsImage(image: ImageBitmap = ImageBitmap.imageResource(R.drawable.test_new_carousel)) {
    Box() {
        Image(
            bitmap = image,
            "",
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
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
    }

}


@Composable
fun ButtonRow() {
    Row(Modifier.padding(horizontal = 16.dp)) {
        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .offset(y = ((-24).dp)),
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
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .offset(y = ((-24).dp)),
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
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun CommonDetailsText(text: String) {
    val paragraphs = text.split("\n")

    Column {
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
    chapter: ChapterModel,
) {
    Row(Modifier.padding(horizontal = 16.dp)) {
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
                    modifier = Modifier.padding(vertical = 13.5.dp),
                )
            }

            is ChapterState.NotWatch -> {
                Text(text = chapter.name, Modifier.weight(1f))
            }

            else -> {}
        }

    }
}