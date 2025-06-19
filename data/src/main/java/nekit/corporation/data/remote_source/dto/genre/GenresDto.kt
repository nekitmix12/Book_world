package nekit.corporation.data.remote_source.dto.genre

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.domain.models.common.Meta

@Serializable
data class GenresDto(
    val data: List<GenreDto>,
    val meta: MetaDto
)
