package nekit.corporation.bookmarks

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.valentinilk.shimmer.shimmer
import nekit.corporation.bookmarks.model.QuoteModel
import nekit.corporation.bookmarks.model.ReadNowBookModel
import nekit.corporation.common_ui.R.drawable.play
import nekit.corporation.common_ui.theme.PurpleGrey40
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.bookAuthorSearch
import nekit.corporation.common_ui.theme.bookNameSearch
import nekit.corporation.common_ui.theme.popularBookAuthor
import nekit.corporation.common_ui.theme.quoteContent
import nekit.corporation.common_ui.theme.white
import nekit.corporation.common_ui.ui_kit.BookItem
import nekit.corporation.common_ui.ui_kit.BookItemWithShimmer
import nekit.corporation.common_ui.ui_kit.MiddleLabel
import nekit.corporation.common_ui.ui_kit.TopLabel


@Composable
fun BookmarksUi(component: BookmarksComponent) {
    val state by component.state.collectAsState()

    LazyColumn(
        Modifier
            .background(background)
            .testTag("bookmarks")
    ) {
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
                    IconButton(component::onPlayClick) {
                        Icon(
                            painterResource(play),
                            "",
                            tint = white,
                            modifier = Modifier.clickable(onClick = component::onPlayClick)
                        )
                    }
                }

            }
            Spacer(Modifier.height(8.dp))

        }
        if (state.reading != null)
            item {
                ReadNow(state.reading!![0], component::onBookClick)
            }
        else
            item {
                BookItemWithShimmer()
            }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.favourite_books)) }
        item { Spacer(Modifier.height(8.dp)) }
        if (state.books != null)
            items(state.books!!) {
                Spacer(Modifier.height(8.dp))
                BookItem(it, component::onBookClick, "markup")
            }
        else
            items(3) {
                Spacer(Modifier.height(8.dp))
                BookItemWithShimmer()
            }
        item { Spacer(Modifier.height(24.dp)) }

        item { MiddleLabel(stringResource(R.string.quotes)) }
        item { Spacer(Modifier.height(8.dp)) }
        if (state.quotes != null)
            items(state.quotes!!) {
                Quote(it)
            }
        else
            items(3) { QuoteWithShimmer() }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuoteWithShimmer() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(accent_light)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shimmer()
    ) {
        Box(
            Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PurpleGrey40)
                .fillMaxWidth(0.3f)
        ) {}
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PurpleGrey40)
                .fillMaxWidth(0.6f)
        ) {}

    }
}


@Composable
fun ReadNow(
    readNowBookModel: ReadNowBookModel,
    onClick: (Long) -> Unit,
) {
    val painter = rememberAsyncImagePainter(model = readNowBookModel.imageUrl)
    val state = painter.state

    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(readNowBookModel.id) }
            .testTag("read now book")
    ) {

        if (state is AsyncImagePainter.State.Loading ||
            state is AsyncImagePainter.State.Error ||
            state is AsyncImagePainter.State.Empty
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp, 126.dp)
                    .shimmer()
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Image(
            painter = painter, "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp, 126.dp)
                .clip(RoundedCornerShape(8.dp)),
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
            ProgressReadBar(readNowBookModel.partInt / 100f, Modifier.padding(top = 16.dp))
            Spacer(Modifier.weight(1f))

        }

    }
}

@Composable
fun ProgressReadBar(part: Float, modifier: Modifier) {
    Row(modifier.testTag("progress bar")) {
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