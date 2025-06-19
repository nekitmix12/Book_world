package nekit.corporation.data.remote_source.dto.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Favorites(
    val data: List<Favorite>,
    val meta: Meta
)
