package nekit.corporation.bookmarks.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nekit.corporation.bookmarks.BookmarksComponent
import nekit.corporation.bookmarks.BookmarksStore.*

class FakeBookmarksComponent : BookmarksComponent {
    override var state = MutableStateFlow(
        State(
            quotes = null,
            books = null,
            reading = null
        )
    )

    override fun onBookClick(bookId: Long) {
    }

    override fun onPlayClick() {
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun BookmarksUIPreview() {
    BookmarksUi(FakeBookmarksComponent())
}