package nekit.corporation.data.remote_source.dto.author

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class AuthorsDto(
    val data:List<AuthorDto>,
    val meta: Meta
)
