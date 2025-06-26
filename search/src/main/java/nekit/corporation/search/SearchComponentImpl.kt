package nekit.corporation.search

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.common.componentCoroutineScope
import nekit.corporation.domain.usecases.GetAuthorsUseCase
import nekit.corporation.domain.usecases.GetBooksByAuthorUseCase
import nekit.corporation.domain.usecases.GetBooksByGenreUseCase
import nekit.corporation.domain.usecases.GetBooksByNameUseCase
import nekit.corporation.domain.usecases.GetGenresUseCase
import nekit.corporation.search.models.AuthorModel
import nekit.corporation.search.models.GenreModel
import nekit.corporation.search.models.RequestModel
import java.util.UUID


@ContributesAssistedFactory(AppScope::class, SearchComponent.Factory::class)
class SearchComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val goToBook: (Long) -> Unit,
    private val searchAuthorUseCase: GetBooksByAuthorUseCase,
    private val getBooksByGenreUseCase: GetBooksByGenreUseCase,
    private val getBooksByNameUseCase: GetBooksByNameUseCase,
    private val getGenreUseCase: GetGenresUseCase,
    private val getAuthorsUseCase: GetAuthorsUseCase,
) : SearchComponent, ComponentContext by componentContext {
    override val state = MutableStateFlow(
        SearchState(
            requests = persistentListOf(),
            genres = persistentListOf(),
            authors = persistentListOf(),
            searchIsOpen = false,
            searchText = "",
            isLoading = true,
            isSearching = false,
            isSearchActive = false,
            books = null
        )
    )
    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            launch {
                getGenreUseCase.execute(GetGenresUseCase.Request).collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                genres = it.data.books.data.map { genre ->
                                    GenreModel(
                                        id = genre.id, genre = genre.name
                                    )
                                }.toImmutableList()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
            }
            launch {
                getAuthorsUseCase.execute(GetAuthorsUseCase.Request).collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                authors = it.data.books.data.map { author ->
                                    AuthorModel(
                                        id = author.id,
                                        imgUrl = "",
                                        name = author.name
                                    )
                                }.toImmutableList()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
            }
            launch(Dispatchers.Default) {
                state.collect {
                    if (state.value.authors != null &&
                        state.value.genres != null
                    )
                        state.value = state.value.copy(
                            isLoading = false
                        )
                }
            }
        }
    }

    override fun onAuthorClick(authorId: Long) {
        state.value = state.value.copy(
            isSearchActive = true,
            isSearching = true,
        )
        coroutineScope.launch {
            searchAuthorUseCase.execute(GetBooksByAuthorUseCase.Request(authorId))
                .collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                isSearching = false,
                                books = it.data.books.toSearchModels()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
        }
    }

    override fun onGenreClick(genreId: Long) {
        state.value = state.value.copy(
            isSearchActive = true,
            isSearching = true,
        )
        coroutineScope.launch {
            getBooksByGenreUseCase.execute(GetBooksByGenreUseCase.Request(genreId))
                .collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                isSearching = false,
                                books = it.data.books.toSearchModels()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
        }
    }

    override fun onRequestClick(request: String) {
        state.value = state.value.copy(
            isSearchActive = true,
            isSearching = true,
            searchText = request
        )
        coroutineScope.launch {
            getBooksByNameUseCase.execute(GetBooksByNameUseCase.Request(request))
                .collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                isSearching = false,
                                books = it.data.books.toSearchModels()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
        }
    }

    override fun onRequestClose(requestId: String) {
        state.value = state.value.copy(
            requests = state.value.requests?.filter { it.name != requestId }?.toImmutableList()
        )
    }

    override fun onSearchByText(text: String) {
        state.value = state.value.copy(
            isSearchActive = true,
            isSearching = true,
            searchText = text,
            requests = state.value.requests?.plus(RequestModel(UUID.randomUUID().toString(), text))
                ?.toImmutableList()
        )
        coroutineScope.launch {
            getBooksByNameUseCase.execute(GetBooksByNameUseCase.Request(text))
                .collect {
                    when (it) {
                        is Result.Success -> {
                            state.value = state.value.copy(
                                isSearching = false,
                                books = it.data.books.toSearchModels()
                            )
                        }

                        is Result.Error -> Log.e(TAG, it.exception)
                    }
                }
        }
    }

    override fun onQueryChange(query: String) {
        state.value = state.value.copy(
            searchText = query
        )
    }


    override fun onSearchClick(isActive: Boolean) {
        state.value = state.value.copy(isSearchActive = isActive)
    }

    override fun onBookClick(bookId: Long) = goToBook(bookId)


    override fun setSearchText(text: String) {
        state.value = state.value.copy(isSearching = true, searchText = text)
    }

    companion object {
        private const val TAG = "SearchComponentImpl"
    }
}