package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class ProgressesDto(
    val data: List<ProgressDto>,
    val meta: MetaDto
)
