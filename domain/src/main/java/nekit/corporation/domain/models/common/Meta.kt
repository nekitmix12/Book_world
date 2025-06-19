package nekit.corporation.domain.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val pagination: Pagination
)
