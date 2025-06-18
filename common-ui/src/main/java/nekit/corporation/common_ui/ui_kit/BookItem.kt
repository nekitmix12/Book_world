package nekit.corporation.common_ui.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.common_ui.theme.bookAuthorSearch
import nekit.corporation.common_ui.theme.bookNameSearch

@Composable
fun BookItem(
    book: BookSearchModel,
    onClick: (String) -> Unit,
    tag:String,
) {

    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick(book.id) }.testTag("book search item $tag")) {
        Image(
            book.image, "", modifier = Modifier
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