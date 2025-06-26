package nekit.corporation.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nekit.corporation.common_ui.R.drawable.close
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.authorText
import nekit.corporation.common_ui.theme.bookAuthorSearch
import nekit.corporation.common_ui.theme.requestText
import nekit.corporation.search.R
import nekit.corporation.search.models.AuthorModel
import nekit.corporation.search.models.GenreModel
import nekit.corporation.search.models.RequestModel

@Composable
fun RequestItem(request: RequestModel, onDelete: (String) -> Unit, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(accent_light)
            .clickable { onClick(request.name) }
            .padding(16.dp)
    ) {
        Icon(painterResource(R.drawable.time_back), "", tint = accent_dark)
        Text(
            text = request.name,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            style = requestText
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(close),
            "",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { onDelete(request.id) },
            tint = accent_dark
        )
    }
}


@Composable
fun AuthorItem(
    author: AuthorModel,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(accent_light)
            .height(IntrinsicSize.Min)
            .testTag("author")
            .clickable { onClick(author.name) }) {
        AsyncImage(
            author.imgUrl,
            "",
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 12.dp)
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            Text(text = author.name, style = authorText)
        }
    }
}

@Composable
fun Genre(
    genre: GenreModel,
    onClick: (Long) -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(accent_light)
            .padding(16.dp)
            .clickable { onClick(genre.id) }
            .testTag("genre"), contentAlignment = Alignment.Center

    ) {
        Text(
            text = genre.genre,
            style = bookAuthorSearch,
        )
    }
}
