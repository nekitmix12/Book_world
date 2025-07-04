package nekit.corporation.details.details

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
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.details.details.DetailsStore.Action
import nekit.corporation.details.details.DetailsStore.Intent
import nekit.corporation.details.details.DetailsStore.Label
import nekit.corporation.details.details.DetailsStore.Msg
import nekit.corporation.details.details.DetailsStore.State
import nekit.corporation.details.models.ShortChapterModel
import nekit.corporation.domain.usecases.GetChaptersUseCase
import nekit.corporation.domain.usecases.books.GetBookByIdUseCase
import nekit.corporation.domain.usecases.favorite.AddToFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.ContainsFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.DeleteFromFavoriteUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressSimpleUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressUseCase

@ContributesAssistedFactory(AppScope::class, DetailsStore.Factory::class)
class DetailsStoreFactory @AssistedInject constructor(
    @Assisted private val storeFactory: StoreFactory,
    @Assisted private val bookId: Long,
    private val getChaptersUseCase: GetChaptersUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val getProgressesUseCase: GetProgressUseCase,
    private val getProgressSimpleUseCase: GetProgressSimpleUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val containsFavoriteUseCase: ContainsFavoriteUseCase,
) {
    fun create(): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadDetails(bookId).also {
                Log.d(TAG, "Bootstrapping with $it")
            }),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {
            init {
                Log.d(TAG, ">>> DetailsStoreFactory.create(bookId=$bookId)")
            }
        }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            Log.d(TAG, ">>> executeAction: $action — currentState = ${state()}")
            when (action) {
                is Action.LoadDetails -> {
                    scope.launch {
                        var localState = state()
                        val jobs = mutableListOf<Job>()
                        jobs.add(launch {
                            getBookById(bookId, state()) { state ->
                                localState = localState.copy(
                                    name = state.name,
                                    documentBookId = state.documentBookId,
                                    description = state.description,
                                    authorName = state.authorName,
                                    image = state.image,
                                )
                                Log.d(TAG, "$localState\n1")
                            }
                        })
                        jobs.add(
                            launch {
                                getIsFavorite(bookId, state()) { state ->
                                    localState = localState.copy(
                                        inFavorite = state.inFavorite,
                                    )
                                }
                                Log.d(TAG, "$localState\n2")
                            })
                        jobs.add(
                            launch {
                                getProgresses(state()) { state ->
                                    localState = localState.copy(
                                        progress = state.progress,
                                    )
                                }
                                Log.d(TAG, "$localState\n3")
                            })
                        jobs.add(
                            launch {
                                getChapters(bookId, state()) { state ->
                                    localState = localState.copy(
                                        chapters = state.chapters,
                                    )
                                }
                                Log.d(TAG, "$localState\n4")
                            })
                        jobs.forEach { it.join() }
                        Log.d(TAG, localState.toString())
                        dispatch(Msg.DetailsLoaded(localState))
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.OnFavoriteClick -> {
                    dispatch(Msg.StartFavoriteLoad)
                    scope.launch {
                        onFavoriteClick(
                            intent.isFavorite, state()
                        ) {
                            dispatch(Msg.FavoriteChanged(it.inFavorite))
                        }
                    }
                }

                Intent.OnPlayClick -> publish(Label.OnPlayClick(getReadingChapter(state())))
            }
        }
    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.FavoriteChanged -> copy(inFavorite = msg.isFavorite, loadFavorite = false)
            is Msg.DetailsLoaded -> copy(
                name = msg.state.name,
                documentBookId = msg.state.documentBookId,
                description = msg.state.description,
                authorName = msg.state.authorName,
                image = msg.state.image,
                inFavorite = msg.state.inFavorite,
                progress = msg.state.progress,
                chapters = msg.state.chapters,
            )

            Msg.StartFavoriteLoad -> copy(loadFavorite = true)
        }

    }

    fun getReadingChapter(state: State): Long {
        return state.chapters?.getOrNull(state.progress)?.id ?: 0L
    }

    suspend fun onFavoriteClick(isFavorite: Boolean, state: State, stateChange: (State) -> Unit) {
        if (isFavorite) deleteFromFavoriteUseCase.execute(DeleteFromFavoriteUseCase.Request(state.documentBookId.toString()))
            .collect {
                when (it) {
                    is Result.Error -> Log.e(TAG, it.exception)
                    is Result.Success -> stateChange(state.copy(inFavorite = !isFavorite))
                }
            }
        else addToFavoriteUseCase.execute(AddToFavoriteUseCase.Request(bookId)).collect {
            when (it) {
                is Result.Error -> Log.e(TAG, it.exception)
                is Result.Success -> stateChange(state.copy(inFavorite = !isFavorite))
            }
        }
    }

    suspend fun getChapters(bookId: Long, state: State, stateChange: (State) -> Unit) {
        getChaptersUseCase.execute(GetChaptersUseCase.Request(bookId)).collect {
            when (it) {
                is Result.Success -> {
                    stateChange(
                        state.copy(
                            chapters = it.data.chapters.data.map {
                                ShortChapterModel(
                                    it.id, it.title
                                )
                            }.toImmutableList()
                        )
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    suspend fun getProgresses(state: State, stateChange: (State) -> Unit) {
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
                                stateChange(
                                    state.copy(
                                        progress = it.data.progressNum
                                    )
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

    suspend fun getIsFavorite(bookId: Long, state: State, stateChange: (State) -> Unit) {
        containsFavoriteUseCase.execute(ContainsFavoriteUseCase.Request(bookId)).collect {
            when (it) {
                is Result.Success -> {
                    stateChange(
                        state.copy(
                            inFavorite = it.data.contains
                        )
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    suspend fun getBookById(bookId: Long, state: State, stateChange: (State) -> Unit) {
        getBookByIdUseCase.execute(GetBookByIdUseCase.Request(bookId)).collect {
            when (it) {
                is Result.Success -> {
                    if (it.data.books.data.isNotEmpty()) stateChange(
                        state.copy(
                            name = it.data.books.data[0].title,
                            documentBookId = it.data.books.data[0].documentId,
                            description = it.data.books.data[0].description,
                            authorName = if (it.data.books.data[0].author.isNotEmpty()) it.data.books.data[0].author[0].name else "",
                            image = it.data.books.data[0].illustrationURL,
                        )
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, it.exception)
                }
            }
        }
    }

    companion object {
        private const val TAG = "DetailsStoreFactory"
    }
}