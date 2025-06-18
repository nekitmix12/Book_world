package nekit.corporation.data.remote_source.dto.progress

import nekit.corporation.data.remote_source.dto.common.MetaDto

data class ProgressesDto(
    val data: List<nekit.corporation.data.remote_source.dto.progress.ProgressDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
