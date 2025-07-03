package nekit.corporation.details.details

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Result
import nekit.corporation.common.utils.componentCoroutineScope
import nekit.corporation.details.models.ShortChapterModel
import nekit.corporation.domain.usecases.GetChaptersUseCase
import nekit.corporation.domain.usecases.books.GetBookByIdUseCase
import nekit.corporation.domain.usecases.favorite.AddToFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.ContainsFavoriteUseCase
import nekit.corporation.domain.usecases.favorite.DeleteFromFavoriteUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressSimpleUseCase
import nekit.corporation.domain.usecases.progresses.GetProgressUseCase

@ContributesAssistedFactory(AppScope::class, DetailsComponent.Factory::class)
class DetailsComponentImpl @AssistedInject constructor(
    @Assisted private val bookId: Long,
    @Assisted componentContext: ComponentContext,
    @Assisted storeFactory: StoreFactory,
    @Assisted val close: () -> Unit,
    @Assisted val openChapter: (Long) -> Unit,
    private val detailsFactory: DetailsStore.Factory
) : ComponentContext by componentContext, DetailsComponent {
    override val state = MutableStateFlow(
        DetailsState(
            loading = true,
        )
    )

    private val store = instanceKeeper.getStore {
        detailsFactory(
            storeFactory = storeFactory,
        ).create()
    }

    private val coroutineScope = componentCoroutineScope()



    override fun onChapterClick(chapterId: Long) = openChapter(chapterId)

    override fun onReadClick() =
        if (state.value.chapters != null) onChapterClick(state.value.chapters!![state.value.progress].id) else Unit

    override fun onFavoriteIconClick() {
        coroutineScope.launch {
            if (state.value.inFavorite)
                addToFavoriteUseCase.execute(AddToFavoriteUseCase.Request(bookId)).collect {

                }
            else if (state.value.documentBookId != null)
                deleteFromFavoriteUseCase.execute(DeleteFromFavoriteUseCase.Request(state.value.documentBookId!!))
                    .collect {

                    }
        }

    }

    override fun onBackClick() = close()

    companion object {
        private const val TAG = "DetailsComponentImpl"
    }
}