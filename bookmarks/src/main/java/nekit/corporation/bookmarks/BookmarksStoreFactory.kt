package nekit.corporation.bookmarks

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.BookmarksStore.Action
import nekit.corporation.bookmarks.BookmarksStore.Intent
import nekit.corporation.bookmarks.BookmarksStore.Label
import nekit.corporation.bookmarks.BookmarksStore.Message
import nekit.corporation.bookmarks.BookmarksStore.State
import nekit.corporation.bookmarks.model.QuoteModel
import nekit.corporation.bookmarks.model.toReadNowBookModel
import nekit.corporation.bookmarks.model.toSearchModel
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.domain.usecases.GetQuotesUseCase
import nekit.corporation.domain.usecases.favorite.GetFavoriteUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressSimpleUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressUseCase

@ContributesAssistedFactory(AppScope::class, BookmarksStore.Factory::class)
class BookmarksStoreFactory @AssistedInject constructor(
    @Assisted private val storeFactory: StoreFactory,
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getProgressUseCase: GetProgressUseCase,
    private val getProgressesUseCase: GetProgressUseCase,
    private val getFavoritesBooksUseCase: GetFavoriteUseCase,
    private val getProgressSimpleUseCase: GetProgressSimpleUseCase,
) {

    fun create(): BookmarksStore =
        object : BookmarksStore, Store<Intent, State, Label> by storeFactory.create(
            name = "BookmarksStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadScreen),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {
        override fun executeAction(action: Action) {
            when (action) {
                Action.LoadScreen -> {
                    var localState = state()
                    scope.launch {
                        val jobs = mutableListOf<Job>()
                        jobs.add(launch {
                            loadProgresses(state()) {
                                localState = localState.copy(
                                    reading = it.reading
                                )
                                Log.d(TAG, "state load 1: $localState")
                            }
                        })
                        jobs.add(launch {
                            getFavorites(state()) {
                                localState = localState.copy(
                                    books = it.books
                                )
                                Log.d(TAG, "state load 2: $localState")
                            }
                        })
                        jobs.add(launch {
                            getQuotes(state()) {
                                localState = localState.copy(
                                    quotes = it.quotes
                                )
                            }
                            Log.d(TAG, "state load 3: $localState")
                        })
                        jobs.forEach { it.join() }
                        dispatch(Message.ScreenLoaded(localState))
                    }
                    Log.d(TAG, "state loaded: $localState")

                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.BookClick -> publish(Label.BookClick(intent.bookId))
                Intent.PlayClick -> {
                    scope.launch {
                        getProgresses(state()) {
                            publish(Label.PlayClick(it))
                        }
                    }

                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.ScreenLoaded -> copy(
                    reading = msg.state.reading, books = msg.state.books, quotes = msg.state.quotes
                )
            }
    }

    private suspend fun loadProgresses(state: State, changeState: (State) -> Unit) {
        getProgressUseCase.process().collect {
            when (it) {
                is Result.Success -> {
                    changeState(
                        state.copy(
                            reading = it.data.books.map { it.first.toReadNowBookModel(it.second) })
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    private suspend fun getFavorites(state: State, changeState: (State) -> Unit) {
        getFavoritesBooksUseCase.execute(GetFavoriteUseCase.Request).collect {
            when (it) {
                is Result.Success -> {
                    if (it.data.books.isNotEmpty()) changeState(
                        state.copy(
                            books = it.data.books.toSearchModel().toImmutableList()
                        )
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    private suspend fun getQuotes(state: State, changeState: (State) -> Unit) {
        getQuotesUseCase.execute(GetQuotesUseCase.Request).collect {
            when (it) {
                is Result.Success -> {
                    changeState(state.copy(quotes = it.data.books.map {
                        QuoteModel(
                            it.first.id,
                            it.first.text,
                            if (it.second.author.isNotEmpty()) it.second.author[0].name else "",
                            it.second.title
                        )
                    }.toImmutableList()))
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)

                }
            }
        }
    }

    suspend fun getProgresses(state: State, getBookId: (Long) -> Unit) {
        getProgressesUseCase.process().collect {
            when (it) {
                is Result.Success -> {
                    getProgressSimpleUseCase.execute(
                        GetProgressSimpleUseCase.Request(
                            it.data.books.map { it.second })
                    ).collect {
                        when (it) {
                            is Result.Error -> Log.e(
                                TAG, it.exception
                            )

                            is Result.Success -> {
                                if (state.books != null && state.books.size < it.data.progressNum)
                                    getBookId(
                                        state.books[it.data.progressNum].id
                                    )
                            }
                        }


                    }
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    companion object {
        private const val TAG = "BookmarksStoreFactory"
    }
}
