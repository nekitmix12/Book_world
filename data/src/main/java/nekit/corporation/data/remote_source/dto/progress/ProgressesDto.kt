package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class ProgressesDto(
    val data: List<nekit.corporation.data.remote_source.dto.progress.ProgressDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
