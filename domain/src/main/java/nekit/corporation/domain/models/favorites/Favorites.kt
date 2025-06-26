package nekit.corporation.domain.models.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Favorites(
    val data: List<Favorite>,
    val meta: Meta
)
