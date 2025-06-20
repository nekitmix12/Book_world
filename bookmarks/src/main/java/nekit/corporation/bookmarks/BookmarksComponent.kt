package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface BookmarksComponent {
    var state: StateFlow<BookmarksState>

    fun onBookClick(bookId: String)

    fun onPlayClick()
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            goToDetails: (Long) -> Unit,
            goToChapter: (Long) -> Unit
        ): BookmarksComponent
    }
}