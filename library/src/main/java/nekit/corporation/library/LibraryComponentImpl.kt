package nekit.corporation.library

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LibraryComponentImpl(
    componentContext: ComponentContext,
    val goToBook: (String) -> Unit
) : LibraryComponent,
    ComponentContext by componentContext {
    override var state: StateFlow<LibraryState> = MutableStateFlow(
        LibraryState(
            newBooks = persistentListOf(),
            popularBooks = persistentListOf(),
        )
    )

    override fun onBookClick(bookId: String) {

    }
}