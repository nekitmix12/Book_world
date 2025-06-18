package nekit.corporation.details.chapter.side_sheet

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.details.models.ShortChapterModel

data class SideSheetState(
    val chapters: ImmutableList<ShortChapterModel>,
    val isVisible: Boolean
)
