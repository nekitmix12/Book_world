package nekit.corporation.search

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope


@ContributesAssistedFactory(AppScope::class, SearchComponent.Factory::class)
class SearchComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val goToBook: (Long) -> Unit
) : SearchComponent,
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