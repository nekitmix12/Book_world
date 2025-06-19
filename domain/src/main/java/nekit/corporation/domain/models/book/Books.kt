package nekit.corporation.domain.models.book

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Books(
    val data: List<Book>,
    val meta: Meta
)