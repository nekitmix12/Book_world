package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.flow.StateFlow

interface BookmarksComponent {
    val state: StateFlow<BookmarksStore.State>

    fun onBookClick(bookId: Long)

    fun onPlayClick()
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            methods: Methods,
            storeFactory: StoreFactory,
        ): BookmarksComponent
    }

    interface Methods {
        fun goToDetails(detailsId: Long)
        fun goToChapter(chapterId: Long)
    }
}