package nekit.corporation.details.chapter.bottom_sheet

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow

class BottomSheetComponentImpl(componentContext: ComponentContext,val onDismissClick: () -> Unit) :
    ComponentContext by componentContext, BottomSheetComponent {
    override val state = MutableStateFlow(
        BottomSheetState(
            fontSize = 14f,
            sizeBetweenStrings = 14f
        )
    )

    override fun onFontSizeChange(size: Float) {
        state.value = state.value.copy(fontSize = size)
    }

    override fun onSpaceChange(space: Float) {
        state.value = state.value.copy(sizeBetweenStrings = space)
    }

    override fun onDismiss() = onDismissClick()
}