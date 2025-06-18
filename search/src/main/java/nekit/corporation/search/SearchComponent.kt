package nekit.corporation.search

import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {
    val state: StateFlow<SearchState>

    fun onAuthorClick(authorId: String)

    fun onGenreClick(genreId: String)

    fun onRequestClick(requestId: String)

    fun onRequestClose(requestId: String)

    fun onSearchClick()

    fun setSearchText(text: String)
}