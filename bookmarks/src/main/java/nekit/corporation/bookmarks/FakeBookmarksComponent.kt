package nekit.corporation.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeBookmarksComponent : BookmarksComponent {
    override var state: StateFlow<BookmarksState> = MutableStateFlow(
        BookmarksState(
            quotes = persistentListOf(),
            books = persistentListOf(),
            reading = null
        )
    )

    override fun onBookClick(bookId: String) {
    }

    override fun onPlayClick() {
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun BookmarksUIPreview() {
    BookmarksUi(FakeBookmarksComponent())
}