package nekit.corporation.data.remote_source.dto.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.domain.models.common.Meta

@Serializable
data class FavoritesDto(
    val data: List<FavoriteDto>,
    val meta: MetaDto
)
