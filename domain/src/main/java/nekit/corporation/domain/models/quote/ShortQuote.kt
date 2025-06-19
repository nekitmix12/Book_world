package nekit.corporation.domain.models.quote

import kotlinx.serialization.Serializable

@Serializable
data class ShortQuote(
    val text: String,
    val bookId: Long
)