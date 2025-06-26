package nekit.corporation.data.remote_source.dto.book

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class ChaptersWithBookAndAuthorDto(
    val data: List<ChapterWithBookAndAuthorDto>,
    val meta: MetaDto
)
