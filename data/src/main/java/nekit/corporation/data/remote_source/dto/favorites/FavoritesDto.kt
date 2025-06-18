package nekit.corporation.data.remote_source.dto.favorites

import nekit.corporation.data.remote_source.dto.common.MetaDto

data class FavoritesDto(
    val data: List<nekit.corporation.data.remote_source.dto.favorites.FavoriteDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
