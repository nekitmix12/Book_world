package nekit.corporation.library

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.domain.usecases.books.GetBooksUseCase
import nekit.corporation.library.LibraryStore.Action
import nekit.corporation.library.LibraryStore.Intent
import nekit.corporation.library.LibraryStore.Label
import nekit.corporation.library.LibraryStore.Message
import nekit.corporation.library.LibraryStore.State

@ContributesAssistedFactory(AppScope::class, LibraryStore.Factory::class)
class LibraryStoreFactory @AssistedInject constructor(
    @Assisted private val storeFactory: StoreFactory,
    private val getBooksUseCase: GetBooksUseCase,
) {

    fun create(): LibraryStore =
        object : LibraryStore, Store<Intent, State, Label> by storeFactory.create(
            name = "LibraryStoreFactory",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadBooks),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {
        override fun executeAction(action: Action) {
            when (action) {
                Action.LoadBooks -> scope.launch {
                    loadBooks(state()) {
                        dispatch(Message.BooksLoaded(it))
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.BookClick -> publish(Label.BookNavigate(intent.bookId))
            }
        }


    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.BooksLoaded -> msg.state.copy(isLoading = false)
            }

    }


    private suspend fun loadBooks(state: State, onStateChange: (State) -> Unit) {
        getBooksUseCase.execute(GetBooksUseCase.Request(PAGE_SIZE, state.booksPage))
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        onStateChange(
                            state.copy(
                                popularBooks = state.popularBooks!!.plus(
                                    result.data.books.data.map { it.toPopularBook() })
                                    .toImmutableList(),
                                newBooks = state.newBooks!!.plus(
                                    result.data.books.data.filter { it.isNew }
                                        .map { it.toNewCarouselBooks() }
                                ).toImmutableList(),
                                booksPage = if (result.data.books.data.isEmpty())
                                    state.booksPage
                                else state.booksPage + 1
                            ))
                    }

                    is Result.Error -> {
                        Log.e(TAG, result.exception)
                    }
                }
            }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val TAG = "LibraryStoreFactory"
    }
}