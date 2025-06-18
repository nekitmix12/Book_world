package nekit.corporation.details.chapter.chapter

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.details.models.Paragraph
import nekit.corporation.details.models.ShortChapterModel

data class ChapterState(
    val isPlay: Boolean,
    val isInteracted: Boolean,
    val text: ImmutableList<Paragraph>,
    val chapter: ShortChapterModel,
    val bookName: String,
    val loading: Boolean
)
