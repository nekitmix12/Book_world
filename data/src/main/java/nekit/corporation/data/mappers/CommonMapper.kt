package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.common.MetaDto
import nekit.corporation.data.remote_source.dto.common.PaginationDto
import nekit.corporation.domain.models.common.Meta
import nekit.corporation.domain.models.common.Pagination

fun Meta.toMetaDto() = MetaDto(pagination.toPaginationDto())

fun Pagination.toPaginationDto() =
    PaginationDto(page = page, pageSize = pageSize, pageCount = pageCount, total = total)

fun MetaDto.toMeta() = Meta(pagination.toPagination())

fun PaginationDto.toPagination() =
    Pagination(page = page, pageSize = pageSize, pageCount = pageCount, total = total)

