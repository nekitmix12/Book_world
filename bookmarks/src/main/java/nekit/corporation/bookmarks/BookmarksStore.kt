package nekit.corporation.bookmarks

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.bookmarks.BookmarksStore.Intent
import nekit.corporation.bookmarks.BookmarksStore.Label
import nekit.corporation.bookmarks.BookmarksStore.State
import nekit.corporation.bookmarks.model.QuoteModel
import nekit.corporation.bookmarks.model.ReadNowBookModel
import nekit.corporation.common.models.BookSearchModel

interface BookmarksStore : Store<Intent, State, Label> {
    data class State(
        val quotes: ImmutableList<QuoteModel>? = null,
        val books: ImmutableList<BookSearchModel>? = null,
        val reading: List<ReadNowBookModel>? = null,
        val isLoading: Boolean = true,
    )

    sealed interface Intent {

        data object PlayClick : Intent

        data class BookClick(val bookId: Long) : Intent
    }

    sealed interface Action {
        data object LoadScreen : Action
    }

    sealed interface Label {
        data class BookClick(val bookId: Long) : Label
        data class PlayClick(val chapterId: Long) : Label
    }

    sealed interface Message {
        data class ScreenLoaded(val state: State) : Message
    }

    fun interface Factory {
        operator fun invoke(storeFactory: StoreFactory): BookmarksStoreFactory
    }
}