package nekit.corporation.domain.models.book

import nekit.corporation.domain.models.common.Meta

data class ChaptersWithBookAndAuthor(
    val data: List<ChapterWithBookAndAuthor>,
    val metaDto: Meta
)
