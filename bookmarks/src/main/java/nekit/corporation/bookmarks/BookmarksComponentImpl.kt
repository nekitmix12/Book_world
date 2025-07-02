package nekit.corporation.bookmarks

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.model.BookmarksState
import nekit.corporation.bookmarks.model.QuoteModel
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.common.utils.componentCoroutineScope
import nekit.corporation.domain.usecases.GetQuotesUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressUseCase
import nekit.corporation.domain.usecases.favorite.GetFavoriteUseCase

@ContributesAssistedFactory(AppScope::class, BookmarksComponent.Factory::class)
class BookmarksComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val methods: BookmarksComponent.Methods,
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getProgressUseCase: GetProgressUseCase,
    private val getFavoritesBooksUseCase: GetFavoriteUseCase,
) : ComponentContext by componentContext, BookmarksComponent {
    override val state = MutableStateFlow(
        BookmarksState()
    )
    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            launch {
                getProgressUseCase.process().collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                reading = it.data.books.map { it.first.toReadNowBookModel(it.second) }
                            )
                        }

                        is Result.Error -> {
                            Log.e(TAG, it.exception)
                        }
                    }
                }
            }
            launch {
                getFavoritesBooksUseCase.execute(GetFavoriteUseCase.Request).collect {
                    when (it) {
                        is Result.Success -> {
                            if (it.data.books.isNotEmpty())
                                state.value = state.value.copy(
                                    books = it.data.books.toSearchModel().toImmutableList()
                                )
                        }

                        is Result.Error -> {
                            Log.e(TAG, it.exception)
                        }
                    }
                }
            }
            launch {
                getQuotesUseCase.execute(GetQuotesUseCase.Request).collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                quotes = it.data.books.map {
                                    QuoteModel(
                                        it.first.id,
                                        it.first.text,
                                        if (it.second.author.isNotEmpty()) it.second.author[0].name else "",
                                        it.second.title
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

    override fun onBookClick(bookId: Long) {
        methods.goToDetails(bookId)
    }

    override fun onPlayClick() {
    }

    companion object {
        private const val TAG = "BookmarksComponentImpl"
    }
}