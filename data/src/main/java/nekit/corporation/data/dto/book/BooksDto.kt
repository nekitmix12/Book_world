package nekit.corporation.data.dto.book

import nekit.corporation.data.dto.common.MetaDto

internal data class BooksDto(
    val data: List<BookDto>,
    val meta: MetaDto
)