package nekit.corporation.data.remote_source.dto.genre

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.data.remote_source.dto.author.AuthorDto

@Serializable
data class GenresDto(
    val data:List<nekit.corporation.data.remote_source.dto.author.AuthorDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
