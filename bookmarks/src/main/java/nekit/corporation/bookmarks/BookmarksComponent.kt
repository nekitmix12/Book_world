package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import nekit.corporation.bookmarks.model.BookmarksState

interface BookmarksComponent {
    val state: StateFlow<BookmarksState>

    fun onBookClick(bookId: Long)

    fun onPlayClick()
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            methods: Methods
        ): BookmarksComponent
    }

    interface Methods {
        fun goToDetails(detailsId: Long)
        fun goToChapter(chapterId: Long)
    }
}