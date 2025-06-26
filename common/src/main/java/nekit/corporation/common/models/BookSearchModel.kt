package nekit.corporation.common.models

data class BookSearchModel(
    val id: Long,
    val name: String,
    val author: String,
    val imageUrl: String
)