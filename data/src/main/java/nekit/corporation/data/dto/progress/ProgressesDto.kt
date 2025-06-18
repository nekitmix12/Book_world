package nekit.corporation.data.dto.progress

import nekit.corporation.data.dto.common.MetaDto

internal data class ProgressesDto(
    val data: List<ProgressDto>,
    val meta: MetaDto
)
