package nekit.corporation.details.chapter.toolbar

import kotlinx.coroutines.flow.StateFlow

interface ToolbarComponent {
    val state: StateFlow<ToolbarState>

    fun onPlayClick()

    fun onChaptersClick()

    fun onNextClick()

    fun onPrevClick()

    fun onSettingsClick()
}