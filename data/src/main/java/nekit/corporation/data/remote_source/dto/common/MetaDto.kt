package nekit.corporation.data.remote_source.dto.common

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Pagination

@Serializable
data class MetaDto(
    val pagination: PaginationDto
)
