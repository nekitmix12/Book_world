package nekit.corporation.details.models

import kotlinx.collections.immutable.ImmutableList

data class Sentence(
    val text:String,
    val styles:ImmutableList<AdditionTextStyle>
)
