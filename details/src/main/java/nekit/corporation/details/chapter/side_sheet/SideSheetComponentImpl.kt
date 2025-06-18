package nekit.corporation.details.chapter.side_sheet

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow

class SideSheetComponentImpl(componentContext: ComponentContext, val onDismissClick: () -> Unit) :
    ComponentContext by componentContext, SideSheetComponent {
    override val state = MutableStateFlow(
        SideSheetState(chapters = persistentListOf(), isVisible = true)
    )

    override fun onChapterClick(chapterId: String) {
    }

    override fun onDismiss() = onDismissClick()
}