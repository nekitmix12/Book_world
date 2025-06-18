package nekit.corporation.details.models

data class ChapterModel(
    val bookName: String,
    val chapter: ShortChapterModel,
    val text: List<List<String>>,
)
