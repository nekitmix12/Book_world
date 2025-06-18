package nekit.corporation.details.chapter.side_sheet

import kotlinx.coroutines.flow.StateFlow
import nekit.corporation.details.models.ChapterState

interface SideSheetComponent {
    val state: StateFlow<SideSheetState>

    fun onChapterClick(chapterId: String)

    fun onDismiss()
}