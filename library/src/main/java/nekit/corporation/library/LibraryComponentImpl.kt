package nekit.corporation.library

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.componentCoroutineScope

@ContributesAssistedFactory(AppScope::class, LibraryComponent.Factory::class)
class LibraryComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val goToBook: (Long) -> Unit,
    @Assisted private val storeFactory: StoreFactory,
    private val libraryFactory: LibraryStore.Factory
) : LibraryComponent, ComponentContext by componentContext {
    override var state = MutableStateFlow(
        LibraryStore.State(
            newBooks = persistentListOf(),
            popularBooks = persistentListOf(),
            isLoading = true,
            booksPage = 1
        )
    )
    private val store = instanceKeeper.getStore {
        libraryFactory(
            storeFactory = storeFactory
        ).create()
    }

    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            store.labels.collect {
                when (it) {
                    is LibraryStore.Label.BookNavigate -> goToBook(it.bookId)
                }
            }
        }
    }


    override fun onBookClick(bookId: Long) = store.accept(LibraryStore.Intent.BookClick(bookId))


}