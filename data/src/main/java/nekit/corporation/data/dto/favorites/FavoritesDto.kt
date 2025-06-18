package nekit.corporation.data.dto.favorites

import nekit.corporation.data.dto.common.MetaDto

internal data class FavoritesDto(
    val data: List<FavoriteDto>,
    val meta: MetaDto
)
