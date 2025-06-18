package nekit.corporation.details.models

sealed class ChapterState {
    data object Passed : ChapterState()
    data object InProgress : ChapterState()
    data object NotWatch : ChapterState()
}