package nekit.corporation.data.remote_source.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    val pagination: nekit.corporation.data.remote_source.dto.common.PaginationDto
)
