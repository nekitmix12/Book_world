package nekit.corporation.data.remote_source.dto.author

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class AuthorsDto(
    val data: List<AuthorDto>,
    val meta: MetaDto
)
