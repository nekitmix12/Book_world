package nekit.corporation.data.remote_source.dto.genre

import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.data.remote_source.dto.author.AuthorDto

data class GenresDto(
    val data:List<nekit.corporation.data.remote_source.dto.author.AuthorDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
