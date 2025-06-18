package nekit.corporation.details.models

import kotlinx.collections.immutable.ImmutableList

data class Paragraph(
    val sentences: ImmutableList<Sentence>
)