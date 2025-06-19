package nekit.corporation.domain.models.quote

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuote(
    val data: ShortQuote
)
