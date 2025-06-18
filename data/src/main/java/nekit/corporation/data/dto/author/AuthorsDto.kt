package nekit.corporation.data.dto.author

import nekit.corporation.data.dto.common.MetaDto

internal data class AuthorsDto(
    val data:List<AuthorDto>,
    val meta: MetaDto
)
