package nekit.corporation.details.details

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.utils.componentCoroutineScope

@ContributesAssistedFactory(AppScope::class, DetailsComponent.Factory::class)
class DetailsComponentImpl @AssistedInject constructor(
    @Assisted private val bookId: Long,
    @Assisted componentContext: ComponentContext,
    @Assisted storeFactory: StoreFactory,
    @Assisted val close: () -> Unit,
    @Assisted val openChapter: (Long) -> Unit,
    private val detailsFactory: DetailsStore.Factory,
) : ComponentContext by componentContext, DetailsComponent {

    private val store = instanceKeeper.getStore {
        detailsFactory(
            storeFactory = storeFactory, bookId = bookId
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = store.stateFlow

    private val scope = componentCoroutineScope()

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onCreate() {
                super.onCreate()
                scope.launch {
                    store.labels.collect {
                        when (it) {
                            is DetailsStore.Label.OnPlayClick -> openChapter(it.chapterId)
                        }
                    }
                }
            }
        })
    }


    override fun onChapterClick(chapterId: Long) = openChapter(chapterId)

    override fun onReadClick() = store.accept(DetailsStore.Intent.OnPlayClick)

    override fun onFavoriteIconClick(isFavorite: Boolean) =
        store.accept(DetailsStore.Intent.OnFavoriteClick(isFavorite))


    override fun onBackClick() = close()

    companion object {
        private const val TAG = "DetailsComponentImpl"
    }
}