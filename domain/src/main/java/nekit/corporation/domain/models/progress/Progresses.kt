package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta
import nekit.corporation.domain.models.progress.Progress

@Serializable
data class Progresses(
    val data: List<Progress>,
    val meta: Meta
)
