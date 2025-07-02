package nekit.corporation.details.details

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.details.models.ShortChapterModel

data class DetailsState(
    val chapters: ImmutableList<ShortChapterModel>? = null,
    val name: String? = null,
    val documentBookId: String ?= null,
    val description: String? = null,
    val authorName: String? = null,
    val readingPercent: Float = 0.0f,
    val inFavorite: Boolean = false,
    val progress: Int = 0,
    val image: String? = null,
    val loading: Boolean
)
