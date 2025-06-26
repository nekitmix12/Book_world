package nekit.corporation.search

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {
    val state: StateFlow<SearchState>

    fun onAuthorClick(authorId: Long)

    fun onGenreClick(genreId: Long)

    fun onRequestClick(request: String)

    fun onRequestClose(requestId: String)

    fun onSearchByText(text: String)

    fun onQueryChange(query: String)

    fun onSearchClick(isActive: Boolean)

    fun onBookClick(bookId: Long)

    fun setSearchText(text: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            goToBook: (Long) -> Unit
        ): SearchComponent
    }
}