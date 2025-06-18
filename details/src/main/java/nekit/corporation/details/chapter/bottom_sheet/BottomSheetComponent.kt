package nekit.corporation.details.chapter.bottom_sheet

import kotlinx.coroutines.flow.StateFlow

interface BottomSheetComponent {
    val state: StateFlow<BottomSheetState>

    fun onFontSizeChange(size: Float)

    fun onSpaceChange(space: Float)

    fun onDismiss()
}