package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nekit.corporation.common.utils.componentCoroutineScope

class BookmarksComponentImpl(
    componentContext: ComponentContext,

    ) : ComponentContext by componentContext, BookmarksComponent {
    override var state: StateFlow<BookmarksState> = MutableStateFlow(
        BookmarksState(
            quotes = persistentListOf(),
            books = persistentListOf(),
            reading = null
        )
    )
    private val coroutineScope = componentCoroutineScope()
    override fun onBookClick(bookId: String) {
    }

    override fun onPlayClick() {
    }
}