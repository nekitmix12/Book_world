package nekit.corporation.data.remote_source.dto.book

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.domain.models.common.Meta

@Serializable
data class BooksDto(
    val data: List<BookDto>,
    val meta: MetaDto
)