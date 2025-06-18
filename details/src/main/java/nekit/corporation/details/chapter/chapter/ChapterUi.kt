package nekit.corporation.details.chapter.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import nekit.corporation.common_ui.R.drawable
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.black
import nekit.corporation.common_ui.theme.chapterName
import nekit.corporation.common_ui.theme.chapterText
import nekit.corporation.common_ui.theme.labelText
import nekit.corporation.common_ui.theme.secondary
import nekit.corporation.common_ui.theme.white
import kotlin.math.roundToInt

//@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun ChapterUi(
    component: ChapterComponent
) {
    val state by component.state.collectAsState()

    val destiny = LocalDensity.current.density
    val lazyListState = rememberLazyListState()
    val isFirstItemVisible by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset == 0 } }
    val isLastItemVisible by remember { derivedStateOf { lazyListState.canScrollForward } }

    val isDragged by lazyListState.interactionSource.collectIsDraggedAsState()

    var currentParagraphIndex by remember { mutableIntStateOf(0) }
    var currentSentencesIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        component.parse()
    }

    LaunchedEffect(state.isPlay) {
        if (state.isPlay) {
            innerLoop@ for (p in state.text.indices) {
                currentParagraphIndex = p
                currentSentencesIndex = 0
                for (s in state.text[p].sentences.indices) {
                    currentSentencesIndex = s
                    delay(state.text[p].sentences[s].text.length * 50L)
                }
            }
            component.stopPlaying()
        }
    }
    LaunchedEffect(isDragged) {
        if (isDragged) {
            component.startInterrupted()
        } else {
            delay(3000)
            component.stopInterrupted()
        }
    }
    val textMeasurer = rememberTextMeasurer()
    LaunchedEffect(state.isPlay, state.isInteracted, currentParagraphIndex, currentSentencesIndex) {
        if (state.isPlay && state.isInteracted.not()) {
            lazyListState.animateScrollToItem(
                index = currentParagraphIndex,
                scrollOffset = if (currentSentencesIndex == 0) {
                    ((-40) * destiny).roundToInt()
                } else {
                    textMeasurer.measure(state.text[currentParagraphIndex].sentences.take(
                        currentSentencesIndex
                    ).joinToString {
                        it.text
                    }).size.height * ((-40) * destiny).roundToInt()
                }
            )
        }
    }
    val currentView = LocalView.current
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(nekit.corporation.common_ui.theme.background).testTag("chapter screen")
    ) {
        Spacer(Modifier.weight(0.1f))
        ChapterLabel(state.bookName, state.chapter.name, component::onBackClick)
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.weight(1f).drawWithContent {
                drawContent()
                if (!isFirstItemVisible) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(nekit.corporation.common_ui.theme.background, nekit.corporation.common_ui.theme.background.copy(alpha = 0f)),
                            startY = 0f,
                            endY = size.height / 10,
                        )
                    )
                }
                if (isLastItemVisible) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(nekit.corporation.common_ui.theme.background, nekit.corporation.common_ui.theme.background.copy(alpha = 0f)),
                            startY = size.height - size.height / 10,
                            endY = size.height,
                        )
                    )
                }
            },
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(state.text) { indexP, paragraph ->
                Text(
                    text = buildAnnotatedString {
                        paragraph.sentences.forEachIndexed { indexS, sentence ->
                            if (!state.isPlay && indexP == currentParagraphIndex && indexS == currentSentencesIndex) {
                                withStyle(SpanStyle(color = nekit.corporation.common_ui.theme.secondary)) {
                                    append(sentence.text)
                                    append(' ')
                                }
                            } else {
                                append(sentence.text)
                                append(' ')
                            }

                            sentence.styles.forEach { style ->
                                val prefixLength = paragraph.sentences.take(indexS)
                                    .joinToString(separator = " ") { it.text }.length
                                addStyle(
                                    style.span,
                                    prefixLength + style.start,
                                    prefixLength + style.end + 1
                                )
                            }
                        }
                    }, color = nekit.corporation.common_ui.theme.black, style = nekit.corporation.common_ui.theme.chapterText
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun ChapterLabel(
    name: String,
    chapter: String,
    onBackClick: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            Modifier.padding(4.dp).size(40.dp).clip(CircleShape).background(nekit.corporation.common_ui.theme.accent_dark)
                .testTag("back from chapter"), contentAlignment = Alignment.Center
        ) {
            IconButton(onBackClick) {
                Icon(
                    painter = painterResource(drawable.back), "", tint = nekit.corporation.common_ui.theme.white
                )
            }
        }
        Column(
            Modifier.weight(1f).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name.uppercase(), style = nekit.corporation.common_ui.theme.labelText)
            Text(text = chapter, style = nekit.corporation.common_ui.theme.chapterName)
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}