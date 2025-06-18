package nekit.corporation.details.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.SpanStyle

@Immutable
data class AdditionTextStyle(
    val span: SpanStyle,
    val start: Int,
    val end: Int
)
