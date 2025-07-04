package nekit.corporation.library

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.library.LibraryStore.Intent
import nekit.corporation.library.LibraryStore.Label
import nekit.corporation.library.LibraryStore.State
import nekit.corporation.library.models.NewCarouselModel
import nekit.corporation.library.models.PopularBookModel

interface LibraryStore : Store<Intent, State, Label> {
    data class State(
        val newBooks: ImmutableList<NewCarouselModel>? = null,
        val popularBooks: ImmutableList<PopularBookModel>? = null,
        val isLoading: Boolean = true,
        val booksPage: Int = 1,
    )

    sealed interface Action {
        data object LoadBooks : Action
    }

    sealed interface Intent {
        data class BookClick(val bookId: Long) : Intent
    }

    sealed interface Message {
        data class BooksLoaded(val state: State) : Message
    }

    sealed interface Label {
        data class BookNavigate(val bookId: Long) : Label
    }

    fun interface Factory {
        operator fun invoke(storeFactory: StoreFactory): LibraryStoreFactory
    }
}