package nekit.corporation.details.details

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.details.models.ShortChapterModel

data class DetailsState(
    val chapters: ImmutableList<ShortChapterModel>,
    val readingPercent: Float,
    val image:ImageBitmap?,
    val loading: Boolean
)
