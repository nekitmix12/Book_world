package nekit.corporation.data.remote_source.dto.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class FavoritesDto(
    val data: List<nekit.corporation.data.remote_source.dto.favorites.FavoriteDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
