package nekit.corporation.details.details

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.details.details.DetailsStore.Intent
import nekit.corporation.details.details.DetailsStore.Label
import nekit.corporation.details.details.DetailsStore.State
import nekit.corporation.details.models.ShortChapterModel

interface DetailsStore : Store<Intent, State, Label> {


    sealed interface Intent {
        data class OnFavoriteClick(val isFavorite: Boolean)
    }

    sealed interface Action {
        data class LoadDetails(val detailsId: Long) : Action
    }

    sealed interface Label {
        data object LoadDetails : Label
        data object OnPlayClick : Label
    }

    data class State(
        val chapters: ImmutableList<ShortChapterModel>? = null,
        val name: String? = null,
        val documentBookId: String? = null,
        val description: String? = null,
        val authorName: String? = null,
        val readingPercent: Float = 0.0f,
        val inFavorite: Boolean = false,
        val progress: Int = 0,
        val image: String? = null,
        val loading: Boolean
    )

    fun interface Factory {
        operator fun invoke(storeFactory: StoreFactory): DetailsStoreFactory
    }
}