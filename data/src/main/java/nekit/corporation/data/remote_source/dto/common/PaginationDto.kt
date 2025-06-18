package nekit.corporation.data.remote_source.dto.common

data class PaginationDto(
    val page: Long,
    val pageSize: Long,
    val pageCount: Long,
    val total: Long
)
