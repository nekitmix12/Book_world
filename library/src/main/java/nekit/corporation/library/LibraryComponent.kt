package nekit.corporation.library

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LibraryComponent {
    val state: MutableStateFlow<LibraryStore.State>

    fun onBookClick(bookId: Long)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            goToBook: (Long) -> Unit,
            storeFactory: StoreFactory,
        ): LibraryComponent
    }
}