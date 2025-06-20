package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.utils.componentCoroutineScope

@ContributesAssistedFactory(AppScope::class, BookmarksComponent.Factory::class)
class BookmarksComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val goToDetails: (Long) -> Unit,
    @Assisted private val goToChapter: (Long) -> Unit
    ) : ComponentContext by componentContext, BookmarksComponent {
    override var state: StateFlow<BookmarksState> = MutableStateFlow(
        BookmarksState(
            quotes = persistentListOf(),
            books = persistentListOf(),
            reading = null
        )
    )
    private val coroutineScope = componentCoroutineScope()
    override fun onBookClick(bookId: String) {
    }

    override fun onPlayClick() {
    }
}