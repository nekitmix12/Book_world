package nekit.corporation.data.dto.genre

import nekit.corporation.data.dto.common.MetaDto
import nekit.corporation.data.dto.author.AuthorDto

internal data class GenresDto(
    val data:List<AuthorDto>,
    val meta: MetaDto
)
