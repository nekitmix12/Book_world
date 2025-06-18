package nekit.corporation.details.chapter.toolbar

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow

class ToolbarComponentImpl(
    componentContext: ComponentContext,
    val onPlay: () -> Unit,
    val onNextChapterClick: () -> Unit,
    val onPrevChapterClick: () -> Unit,
    val openSettings: () -> Unit,
    val openChaptersList: () -> Unit
) :
    ComponentContext by componentContext, ToolbarComponent {
    override val state = MutableStateFlow(ToolbarState(isPlay = false))

    override fun onPlayClick() {
        onPlay()
        state.value = state.value.copy(isPlay = !state.value.isPlay)
    }

    override fun onChaptersClick() = openChaptersList()

    override fun onNextClick() = onNextChapterClick()

    override fun onPrevClick() = onPrevChapterClick()

    override fun onSettingsClick() = openSettings()
}