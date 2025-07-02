package nekit.corporation.details.details

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {
    val state: StateFlow<DetailsState>

    fun onChapterClick(chapterId: Long)

    fun onReadClick()

    fun onAddToFavoriteClick()

    fun onBackClick()


    fun interface Factory {
        operator fun invoke(
            bookId: Long,
            componentContext: ComponentContext,
            close: () -> Unit,
            openChapter: (Long) -> Unit,
        ): DetailsComponent
    }
}

