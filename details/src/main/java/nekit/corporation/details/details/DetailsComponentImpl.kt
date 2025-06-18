package nekit.corporation.details.details

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope

@ContributesAssistedFactory(AppScope::class, DetailsComponent.Factory::class)
class DetailsComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val close: () -> Unit,
    @Assisted val openChapter: (String) -> Unit
) :
    ComponentContext by componentContext, DetailsComponent {
    override val state: StateFlow<DetailsState> = MutableStateFlow(
        DetailsState(
            chapters = persistentListOf(),
            readingPercent = 0.4f,
            image = null,
            loading = false,
        )
    )

    override fun onChapterClick(chapterId: String) {
    }

    override fun onReadClick() {
    }

    override fun onAddToFavoriteClick() {
    }

    override fun onBackClick() {

    }

    override fun nextChapter() {
        TODO("Not yet implemented")
    }

    override fun prevChapter() {
        TODO("Not yet implemented")
    }
}