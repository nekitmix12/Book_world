package nekit.corporation.data.remote_source.dto.genre

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Genres(
    val data:List<Genre>,
    val meta: Meta
)
