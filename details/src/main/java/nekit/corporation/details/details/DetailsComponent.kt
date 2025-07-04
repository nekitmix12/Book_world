package nekit.corporation.details.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {
    val state: StateFlow<DetailsStore.State>

    fun onChapterClick(chapterId: Long)

    fun onReadClick()

    fun onFavoriteIconClick(isFavorite: Boolean)

    fun onBackClick()


    fun interface Factory {
        operator fun invoke(
            bookId: Long,
            componentContext: ComponentContext,
            storeFactory: StoreFactory,
            close: () -> Unit,
            openChapter: (Long) -> Unit,
        ): DetailsComponent
    }
}

