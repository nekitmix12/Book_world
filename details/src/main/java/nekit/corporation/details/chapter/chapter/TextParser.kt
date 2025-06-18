package nekit.corporation.details.chapter.chapter

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import nekit.corporation.details.models.AdditionTextStyle
import nekit.corporation.details.models.Paragraph
import nekit.corporation.details.models.Sentence

object TextParser {
    fun parse(text: String): ImmutableList<Paragraph> {
        return text.split("\n")
            .map { paragraphText ->
                val sentences = paragraphText
                    .split(Regex("""(?<!\n\.\w.)(?<![A-Z][a-z]\.)(?<=[.!?])\n"""))
                    .map { sentence ->
                        var italicIndexStart: Int? = null
                        val span = mutableListOf<AdditionTextStyle>()
                        var index = 0
                        val resultString = StringBuilder()
                        while (index < sentence.length) {
                            if (sentence[index] == '*') {
                                if (italicIndexStart == null)
                                    italicIndexStart = resultString.length
                                else {
                                    span.add(
                                        AdditionTextStyle(
                                            SpanStyle(fontStyle = FontStyle.Italic),
                                            italicIndexStart,
                                            resultString.length
                                        )
                                    )
                                    italicIndexStart = null
                                }
                                index++
                            } else {
                                resultString.append(sentence[index])
                                index++
                            }
                        }
                        Sentence(resultString.toString(), span.toImmutableList())
                    }
                Paragraph(sentences = sentences.toImmutableList())
            }.toImmutableList()
    }
}