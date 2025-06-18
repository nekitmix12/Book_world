package nekit.corporation.details.chapter.chapter

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import nekit.corporation.details.chapter.bottom_sheet.BottomSheetComponent
import nekit.corporation.details.chapter.side_sheet.SideSheetComponent
import nekit.corporation.details.chapter.toolbar.ToolbarComponent

interface ChapterComponent {
    val state: StateFlow<ChapterState>

    val bottomSheet: Value<ChildSlot<*, BottomSheetComponent>>
    val sideSheet: Value<ChildSlot<*, SideSheetComponent>>

    val toolbarComponent: ToolbarComponent

    fun stopPlaying()

    fun parse()

    fun startInterrupted()

    fun stopInterrupted()

    fun onBackClick()

    fun onChangePlaying()

    fun changeOnNextChapter()

    fun changeOnPrevChapter()

    fun openSettings()

    fun openChapters()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            chapterId: String,
            onClose: () -> Unit
        ): ChapterComponent
    }
}

