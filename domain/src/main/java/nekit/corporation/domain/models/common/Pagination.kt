package nekit.corporation.domain.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val page: Long,
    val pageSize: Long,
    val pageCount: Long,
    val total: Long
)
