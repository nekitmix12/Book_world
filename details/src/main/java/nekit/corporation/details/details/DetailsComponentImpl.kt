package nekit.corporation.details.details

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.common.utils.componentCoroutineScope
import nekit.corporation.details.models.ShortChapterModel
import nekit.corporation.domain.usecases.GetChaptersUseCase
import nekit.corporation.domain.usecases.books.GetBookByIdUseCase
import nekit.corporation.domain.usecases.favorite.AddToFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.ContainsFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.DeleteFromFavoriteUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressSimpleUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressUseCase

@ContributesAssistedFactory(AppScope::class, DetailsComponent.Factory::class)
class DetailsComponentImpl @AssistedInject constructor(
    @Assisted private val bookId: Long,
    @Assisted componentContext: ComponentContext,
    @Assisted val close: () -> Unit,
    @Assisted val openChapter: (Long) -> Unit,
    private val getChaptersUseCase: GetChaptersUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val getProgressesUseCase: GetProgressUseCase,
    private val getProgressSimpleUseCase: GetProgressSimpleUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val containsFavoriteUseCase: ContainsFavoriteUseCase,
) : ComponentContext by componentContext, DetailsComponent {
    override val state = MutableStateFlow(
        DetailsState(
            loading = true,
        )
    )

    private val coroutineScope = componentCoroutineScope()

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                coroutineScope.launch {
                    launch {
                        getBookByIdUseCase.execute(GetBookByIdUseCase.Request(bookId)).collect {
                            when (it) {
                                is Result.Success -> {
                                    if (it.data.books.data.isNotEmpty()) state.value =
                                        state.value.copy(
                                            name = it.data.books.data[0].title,
                                            documentBookId = it.data.books.data[0].documentId,
                                            description = it.data.books.data[0].description,
                                            authorName = if (it.data.books.data[0].author.isNotEmpty()) it.data.books.data[0].author[0].name else "",
                                            image = it.data.books.data[0].illustrationURL,
                                        )
                                }

                                is Result.Error -> {
                                    Log.e(TAG, it.exception)
                                }
                            }
                        }
                    }
                    launch {
                        containsFavoriteUseCase.execute(ContainsFavoriteUseCase.Request(bookId))
                            .collect {
                                when (it) {
                                    is Result.Success -> {
                                        state.value = state.value.copy(
                                            inFavorite = it.data.contains
                                        )
                                    }

                                    is Result.Error -> {
                                        Log.e(TAG, it.exception)
                                    }
                                }
                            }
                    }
                    launch {
                        getProgressesUseCase.process().collect {
                            when (it) {
                                is Result.Success -> {
                                    getProgressSimpleUseCase.execute(
                                        GetProgressSimpleUseCase.Request(
                                            it.data.books.map { it.second })
                                    ).collect {
                                        when (it) {
                                            is Result.Error -> Log.e(TAG, it.exception)
                                            is Result.Success -> {
                                                state.value = state.value.copy(
                                                    progress = it.data.progressNum
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
                    launch {
                        getChaptersUseCase.execute(GetChaptersUseCase.Request(bookId)).collect {
                            when (it) {
                                is Result.Success -> {
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

    override fun onChapterClick(chapterId: Long) = openChapter(chapterId)

    override fun onReadClick() =
        if (state.value.chapters != null) onChapterClick(state.value.chapters!![state.value.progress].id) else Unit

    override fun onAddToFavoriteClick() {
        coroutineScope.launch {
            if (state.value.inFavorite)
                addToFavoriteUseCase.execute(AddToFavoriteUseCase.Request(bookId)).collect {

                }
            else if (state.value.documentBookId != null)
                deleteFromFavoriteUseCase.execute(DeleteFromFavoriteUseCase.Request(state.value.documentBookId!!))
                    .collect {

                    }
        }

    }

    override fun onBackClick() = close()

    companion object {
        private const val TAG = "DetailsComponentImpl"
    }
}