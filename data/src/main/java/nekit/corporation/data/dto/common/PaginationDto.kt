package nekit.corporation.data.dto.common

internal data class PaginationDto(
    val page: Long,
    val pageSize: Long,
    val pageCount: Long,
    val total: Long
)
