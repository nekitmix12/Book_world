package nekit.corporation.bookmarks

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.BookmarksStore.Label
import nekit.corporation.bookmarks.BookmarksStore.State
import nekit.corporation.common.AppScope
import nekit.corporation.common.utils.componentCoroutineScope

@ContributesAssistedFactory(AppScope::class, BookmarksComponent.Factory::class)
class BookmarksComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val methods: BookmarksComponent.Methods,
    @Assisted private val storeFactory: StoreFactory,
    private val bookmarksStoreFactory: BookmarksStore.Factory
) : ComponentContext by componentContext, BookmarksComponent {
    private val coroutineScope = componentCoroutineScope()

    private val store = instanceKeeper.getStore {
        bookmarksStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = store.stateFlow

    init {
        coroutineScope.launch {
            store.labels.collect {
                when (it) {
                    is Label.BookClick -> methods.goToDetails(it.bookId)
                    is Label.PlayClick -> methods.goToChapter(it.chapterId)
                }
            }
        }
    }

    override fun onBookClick(bookId: Long) {
        methods.goToDetails(bookId)
    }

    override fun onPlayClick() {
    }

    companion object {
        private const val TAG = "BookmarksComponentImpl"
    }
}