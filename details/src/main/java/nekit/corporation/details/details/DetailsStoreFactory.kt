package nekit.corporation.details.details

import android.util.Log
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.details.details.DetailsStore.*
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
            name = "AuthStore",
            initialState = State(loading = true),
            bootstrapper = SimpleBootstrapper(Action.LoadRefreshToken),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        )

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                coroutineScope.launch {
                    launch {
                        getBookByIdUseCase.execute(GetBookByIdUseCase.Request(bookId)).collect {
                            when (it) {
                                is nekit.corporation.common.Result.Success -> {
                                    if (it.data.books.data.isNotEmpty()) state.value =
                                        state.value.copy(
                                            name = it.data.books.data[0].title,
                                            documentBookId = it.data.books.data[0].documentId,
                                            description = it.data.books.data[0].description,
                                            authorName = if (it.data.books.data[0].author.isNotEmpty()) it.data.books.data[0].author[0].name else "",
                                            image = it.data.books.data[0].illustrationURL,
                                        )
                                }

                                is nekit.corporation.common.Result.Error -> {
                                    Log.e(TAG, it.exception)
                                }
                            }
                        }
                    }
                    launch {
                        containsFavoriteUseCase.execute(ContainsFavoriteUseCase.Request(bookId))
                            .collect {
                                when (it) {
                                    is nekit.corporation.common.Result.Success -> {
                                        state.value = state.value.copy(
                                            inFavorite = it.data.contains
                                        )
                                    }

                                    is nekit.corporation.common.Result.Error -> {
                                        Log.e(TAG, it.exception)
                                    }
                                }
                            }
                    }
                    launch {
                        getProgressesUseCase.process().collect {
                            when (it) {
                                is nekit.corporation.common.Result.Success -> {
                                    getProgressSimpleUseCase.execute(
                                        GetProgressSimpleUseCase.Request(
                                            it.data.books.map { it.second })
                                    ).collect {
                                        when (it) {
                                            is nekit.corporation.common.Result.Error -> Log.e(
                                                TAG,
                                                it.exception
                                            )

                                            is nekit.corporation.common.Result.Success -> {
                                                state.value = state.value.copy(
                                                    progress = it.data.progressNum
                                                )
                                            }
                                        }


                                    }
                                }

                                is nekit.corporation.common.Result.Error -> {
                                    Log.e(TAG, it.exception)
                                }
                            }
                        }
                    }
                    launch {
                        getChaptersUseCase.execute(GetChaptersUseCase.Request(bookId)).collect {
                            when (it) {
                                is nekit.corporation.common.Result.Success -> {
                                    state.value = state.value.copy(
                                        chapters = it.data.chapters.data.map {
                                            ShortChapterModel(
                                                it.id, it.title
                                            )
                                        }.toImmutableList()
                                    )
                                }

                                is Result.Error -> {
                                    Log.e(TAG, it.exception)
                                }
                            }
                        }
                    }
                }
            }
        })

    }
}