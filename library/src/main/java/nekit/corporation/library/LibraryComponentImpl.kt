package nekit.corporation.library

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.common.componentCoroutineScope
import nekit.corporation.domain.usecases.books.GetBooksUseCase

@ContributesAssistedFactory(AppScope::class, LibraryComponent.Factory::class)
class LibraryComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val goToBook: (Long) -> Unit,
    private val getBooksUseCase: GetBooksUseCase,
) : LibraryComponent, ComponentContext by componentContext {
    override var state = MutableStateFlow(
        LibraryState(
            newBooks = persistentListOf(),
            popularBooks = persistentListOf(),
            isLoading = true,
            booksPage = 1
        )
    )


    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            launch {
                Log.i(TAG, state.value.toString())
                getBooksUseCase.execute(GetBooksUseCase.Request(PAGE_SIZE, state.value.booksPage))
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                state.value =
                                    state.value.copy(
                                        popularBooks = state.value.popularBooks!!.plus(
                                            result.data.books.data.map { it.toPopularBook() })
                                            .toImmutableList(),
                                        newBooks = state.value.newBooks!!.plus(
                                            result.data.books.data.filter { it.isNew }
                                                .map { it.toNewCarouselBooks() }
                                        ).toImmutableList(),
                                        booksPage = if (result.data.books.data.isEmpty())
                                            state.value.booksPage
                                        else state.value.booksPage + 1
                                    )
                            }

                            is Result.Error -> {
                                Log.e(TAG, result.exception)
                            }
                        }
                    }
            }

            launch {
                state.collect {
                    if (state.value.popularBooks!!.isNotEmpty() && state.value.popularBooks!!.isNotEmpty())
                        state.value = state.value.copy(isLoading = false)
                }
            }
        }
    }


    override fun onBookClick(bookId: Long) = goToBook(bookId)

    companion object {
        private const val PAGE_SIZE = 10
        private const val TAG = "LibraryComponentImpl"
    }
}