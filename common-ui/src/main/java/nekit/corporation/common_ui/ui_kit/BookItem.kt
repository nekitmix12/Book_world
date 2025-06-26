package nekit.corporation.common_ui.ui_kit

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.common_ui.theme.PurpleGrey40
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.bookAuthorSearch
import nekit.corporation.common_ui.theme.bookNameSearch

@Composable
fun BookItem(
    book: BookSearchModel,
    onClick: (Long) -> Unit,
    tag: String,
) {

    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick(book.id) }
            .testTag("book search item $tag")
    ) {
        AsyncImage(
            book.imageUrl, "", modifier = Modifier
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
            Text(text = book.name, style = bookNameSearch)
            Text(text = book.author, style = bookAuthorSearch)
            Spacer(Modifier.weight(1f))
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookItemWithShimmer() {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(accent_medium)
            .padding(horizontal = 16.dp)
            .shimmer()

    ) {
        Box(
            modifier = Modifier
                .height(126.dp)
                .padding(12.dp)
                .width(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PurpleGrey40)
        )
        Column(
            modifier = Modifier
                .height(126.dp)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.28f)
                    .height(24.dp)
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PurpleGrey40)
            ) {}
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PurpleGrey40)
            ) {}
            Spacer(Modifier.weight(1f))
        }
    }
}