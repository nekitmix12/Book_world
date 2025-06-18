package nekit.corporation.bookmarks

import kotlinx.coroutines.flow.StateFlow

interface BookmarksComponent {
    var state: StateFlow<BookmarksState>

    fun onBookClick(bookId: String)

    fun onPlayClick()

}