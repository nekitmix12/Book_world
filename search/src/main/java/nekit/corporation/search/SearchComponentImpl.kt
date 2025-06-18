package nekit.corporation.search

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchComponentImpl(componentContext:ComponentContext) : SearchComponent,
    ComponentContext by componentContext {
    override val state = MutableStateFlow(
        SearchState(
            requests = persistentListOf(),
            genres = persistentListOf(),
            authors = persistentListOf(),
            searchIsOpen = false,
            searchText = "",
            isLoading = true
        )
    )

    override fun onAuthorClick(authorId: String) {
    }

    override fun onGenreClick(genreId: String) {
    }

    override fun onRequestClick(requestId: String) {
    }

    override fun onRequestClose(requestId: String) {
    }

    override fun onSearchClick() {
    }

    override fun setSearchText(text: String) {
        state.value = state.value.copy(searchIsOpen = true, searchText = text)
    }
}