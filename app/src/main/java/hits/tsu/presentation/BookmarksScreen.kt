package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hits.tsu.R
import hits.tsu.presentation.models.BookSearchModel
import hits.tsu.presentation.models.QuoteModel
import hits.tsu.presentation.models.ReadNowBookModel
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_light
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.background
import hits.tsu.presentation.theme.bookAuthorSearch
import hits.tsu.presentation.theme.bookNameSearch
import hits.tsu.presentation.theme.popularBookAuthor
import hits.tsu.presentation.theme.quoteContent
import hits.tsu.presentation.theme.white
import java.util.UUID


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun BookmarksScreen(navController: NavController = rememberNavController()) {

    val listQuote = listOf(
        QuoteModel(
            UUID.randomUUID().toString(),
            "Я все еще жив",
            "Код да Винчи",
            "Дэн Браун"
        ),
        QuoteModel(
            UUID.randomUUID().toString(),
            "Я все еще жив",
            "Код да Винчи",
            "Дэн Браун"
        ),
        QuoteModel(
            UUID.randomUUID().toString(),
            "Я все еще жив",
            "Код да Винчи",
            "Дэн Браун"
        ),
    )

    val listFavoriteBook = listOf(
        BookSearchModel(
            UUID.randomUUID().toString(),
            "SWift для детей",
            "Мэтт Маккарти, Глория Уинквист",
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            )
        ),
        BookSearchModel(
            UUID.randomUUID().toString(),
            "SWift для детей",
            "Мэтт Маккарти, Глория Уинквист",
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            )
        ),
        BookSearchModel(
            UUID.randomUUID().toString(),
            "SWift для детей",
            "Мэтт Маккарти, Глория Уинквист",
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            )
        ),
    )

    val readNow = listOf(
        ReadNowBookModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Код да винчи",
            "Пролог",
            0.1f
        ),
        ReadNowBookModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Код да винчи",
            "Пролог",
            0.1f
        ),
        ReadNowBookModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Код да винчи",
            "Пролог",
            0.1f
        ),
    )


    val onSearchItemClick: (BookSearchModel) -> Unit = {
        navController.navigate(Screens.BookDetails(it.id))
    }


    val onReadNowBookClick: (ReadNowBookModel) -> Unit = {
        navController.navigate(Screens.BookDetails(it.id))
    }

    val onPlayClick: () -> Unit = {
        if (readNow.isNotEmpty())
            navController.navigate(Screens.Chapter(readNow[0].id))
    }

    LazyColumn(Modifier.background(background)) {
        item { Spacer(Modifier.height((LocalConfiguration.current.screenHeightDp * 0.085f).dp)) }
        item { TopLabel(stringResource(R.string.notes)) }
        item { Spacer(Modifier.height(24.dp)) }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MiddleLabel(stringResource(R.string.read_now))
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .padding(end = 16.dp)
                        .padding(4.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(accent_dark),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onPlayClick) {
                        Icon(
                            painterResource(R.drawable.play),
                            "",
                            tint = white,
                            modifier = Modifier.clickable {
                                if (readNow.isNotEmpty())
                                    navController.navigate(Screens.Chapter(readNow[0].id))
                            }
                        )
                    }
                }

            }
        }
        items(readNow) {
            Spacer(Modifier.height(8.dp))
            ReadNow(it, onReadNowBookClick)
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.favourite_books)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(listFavoriteBook) {
            Spacer(Modifier.height(8.dp))
            BookSearchItem(it, onSearchItemClick)
        }
        item { Spacer(Modifier.height(24.dp)) }

        item { MiddleLabel(stringResource(R.string.quotes)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(listQuote) {
            Quote(it)
        }

    }
}


@Composable
fun Quote(quote: QuoteModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(accent_light)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = quote.content, style = quoteContent)
        Spacer(Modifier.height(8.dp))
        Text(text = quote.book + " • " + quote.author, style = popularBookAuthor)
    }
}


@Composable
fun ReadNow(
    readNowBookModel: ReadNowBookModel,
    onClick: (ReadNowBookModel) -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(readNowBookModel) }
    ) {
        Image(
            readNowBookModel.image, "", modifier = Modifier
                .height(126.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier
                .height(126.dp)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.weight(1f))
            Text(text = readNowBookModel.name, style = bookNameSearch)
            Text(text = readNowBookModel.part, style = bookAuthorSearch)
            ProgressReadBar(readNowBookModel.partInt, Modifier.padding(top = 16.dp))
            Spacer(Modifier.weight(1f))

        }

    }
}

@Composable
fun ProgressReadBar(part: Float, modifier: Modifier) {
    Row(modifier) {
        Spacer(
            Modifier
                .weight(part)
                .height(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(accent_dark)
        )
        Spacer(Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .weight(1f - part)
                .height(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(accent_medium)

        ) {
            Spacer(
                Modifier
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(accent_dark)
                    .size(4.dp)
            )
        }
    }
}