package nekit.corporation.library

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface LibraryComponent {
    val state: StateFlow<LibraryState>

    fun onBookClick(bookId: Long)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            goToBook: (Long) -> Unit
        ): LibraryComponent
    }
}