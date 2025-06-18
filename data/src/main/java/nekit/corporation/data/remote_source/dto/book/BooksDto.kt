package nekit.corporation.data.remote_source.dto.book

import nekit.corporation.data.remote_source.dto.common.MetaDto

data class BooksDto(
    val data: List<nekit.corporation.data.remote_source.dto.book.BookDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)