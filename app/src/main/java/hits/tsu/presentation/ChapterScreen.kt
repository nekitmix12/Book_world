package hits.tsu.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hits.tsu.R
import hits.tsu.presentation.models.ChapterModel
import hits.tsu.presentation.models.ChapterState
import hits.tsu.presentation.models.MarkupModel
import hits.tsu.presentation.models.ShortChapterModel
import hits.tsu.presentation.models.TextSettingsModel
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_light
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.authorText
import hits.tsu.presentation.theme.background
import hits.tsu.presentation.theme.chapterName
import hits.tsu.presentation.theme.chapterText
import hits.tsu.presentation.theme.detailsBody
import hits.tsu.presentation.theme.detailsSelectedBody
import hits.tsu.presentation.theme.labelText
import hits.tsu.presentation.theme.secondary
import hits.tsu.presentation.theme.white
import java.util.UUID


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun ChapterScreen(
    navController: NavController = rememberNavController(),
    chapterId: String = "null",
) {
    val chapter = ChapterModel(bookName = "код да винчи",
        chapter = ShortChapterModel(
            id = chapterId, name = "Пролог", state = ChapterState.InProgress
        ),
        text = ("Париж, Лувр\u2002821.46\n" + "Знаменитый куратор Жак Соньер, пошатываясь, прошел под сводчатой аркой Большой галереи и устремился к первой попавшейся ему на глаза картине, полотну Караваджо. Ухватился руками за позолоченную раму и стал тянуть ее на себя, пока шедевр не сорвался со стены и не рухнул на семидесятилетнего старика Соньера, погребя его под собой.\n" + "Как и предполагал Соньер, неподалеку с грохотом опустилась " + "металлическая решетка, преграждающая доступ в этот зал. Паркетный пол содрогнулся. Где-то завыла сирена игнализации.\n" + "Несколько секунд куратор лежал неподвижно, хватая ртом " + "воздух и пытаясь сообразить, на каком свете находится. Я все еще жив. Потом он выполз из-под полотна и начал судорожно ози\u0002раться в поисках места, где можно спрятаться.\n" + "Голос прозвучал неожиданно близко:\n" + "— Не двигаться.\n" + "Стоявший на четвереньках куратор похолодел, потом медлен\u0002но обернулся. Всего в пятнадцати футах от него, за решеткой, высилась внушительная и грозная фигура его преследователя. Вы\u0002сокий, широкоплечий, с мертвенно-бледной кожей и редкими " + "белыми волосами. Белки розовые, а зрачки угрожающего темно\u0002красного цвета. Альбинос достал из кармана пистолет, сунул " + "длинный ствол в отверстие между железными прутьями и при\u0002целился в куратора.\n").split(
            "\n"
        ).map { line ->
            line.split(".")
        }

    )


    val isVisibleSideSheet = remember { mutableStateOf(false) }
    var isVisibleBottomSheet by remember { mutableStateOf(false) }
    var isPlay by remember { mutableStateOf(false) }
    var textState by remember {
        mutableStateOf(
            TextSettingsModel(
                fontSize = 14, spaceBetween = 16
            )
        )
    }

    val chapters = listOf(
        ShortChapterModel(
            UUID.randomUUID().toString(), "Факты", ChapterState.Passed
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Пролог", ChapterState.InProgress
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ShortChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Spacer(Modifier.weight(0.1f))
        ChapterLabel(chapter.bookName, chapter.chapter.name)
        Spacer(Modifier.height(16.dp))
        Box(Modifier.weight(1f)) {
            ChapterText(
                chapter.text, listOf(
                    MarkupModel(
                        9,
                        SpanStyle(fontStyle = FontStyle.Italic)
                    ),
                    MarkupModel(
                        10,
                        SpanStyle(color = secondary)
                    )
                )
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to background, 1f to Color.Transparent
                        )
                    )
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to Color.Transparent, 1f to background
                        )
                    )
            )
        }
        Spacer(Modifier.height(16.dp))
        ChapterBottomBar(
            onTokClick = { isVisibleSideSheet.value = true },
            onForwardClick = {},
            onBackClick = {},
            onPlayClick = { isPlay = !isPlay },
            onSettingsClick = { isVisibleBottomSheet = true },
            isPlay = isPlay
        )
    }

    if (isVisibleSideSheet.value) ChapterSideSheet(chapters, isVisibleSideSheet)
    if (isVisibleBottomSheet) ChapterBottomSheet(textState, {
        textState = it
        isVisibleBottomSheet = false
    })
}

@Composable
fun ChapterLabel(
    name: String,
    chapter: String,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            Modifier
                .padding(4.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(accent_dark),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.back), "", tint = white
            )
        }
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name.uppercase(), style = labelText)
            Text(text = chapter, style = chapterName)
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
@Stable
fun ChapterText(
    text: List<List<String>>,
    listMarkup: List<MarkupModel>,
    textSize: Int = 14,
    spaceBetween: Int = 16,
) {
    var markupIterator by remember { mutableIntStateOf(0) }
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(text.size) {
/*
            Log.i("ChapterScreen", "text.size: ${text.size}")
*/
            val paragraph = text[it]
            val newString = buildAnnotatedString {
                for (i in paragraph.indices) {
                   /* Log.i("ChapterScreen", "i $i")
                    Log.i("ChapterScreen", "markupIterator $markupIterator")*/
                    if (markupIterator < listMarkup.size && i * it == listMarkup[markupIterator].element) {

                        withStyle(listMarkup[markupIterator].markup) {
                            append(paragraph[i])
                        }
                        Log.i("ChapterScreen", "win")
                        if (markupIterator < listMarkup.size) markupIterator++
                    } else
                        append(paragraph[i])
                }
            }
            Text(
                text = newString, style = chapterText, fontSize = textSize.sp
            )
            Spacer(Modifier.height(spaceBetween.dp))
        }

    }


}


@Composable
fun ChapterBottomBar(
    onTokClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPlayClick: () -> Unit,
    isPlay: Boolean,
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(R.drawable.slide_back),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = onTokClick) {
                Icon(
                    painterResource(R.drawable.more),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = onForwardClick) {
                Icon(
                    painterResource(R.drawable.next),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    painterResource(R.drawable.settings),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPlayClick,
                containerColor = accent_light,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {

                Icon(
                    painter = painterResource(if (isPlay) R.drawable.stop else R.drawable.play),
                    "",
                    tint = accent_dark
                )
            }
        },
        containerColor = accent_dark,
    )
}

@SuppressLint("AutoboxingStateValueProperty")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ChapterBottomSheet(
    textSettingsModel: TextSettingsModel,
    onDismiss: (TextSettingsModel) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss(textSettingsModel) }, containerColor = background
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(text = stringResource(R.string.settings), style = labelText)
            Spacer(Modifier.height(28.dp))
            SettingSlider(stringResource(R.string.font_size))
            Spacer(Modifier.height(28.dp))
            SettingSlider(stringResource(R.string.string_space))
            Spacer(Modifier.height(28.dp))

        }
    }
}

@Composable
fun SettingSlider(text: String) {
    var value by remember { mutableFloatStateOf(14f) }
    Column() {
        Text(text = text, style = authorText)
        Slider(
            value = value,
            onValueChange = { value = it },
            Modifier,
            steps = 11,
            valueRange = 11f..19f,
            onValueChangeFinished = {},
            colors = SliderColors(
                accent_dark,
                accent_medium,
                accent_dark,
                accent_medium,
                accent_dark,
                Color.Red,
                Color.Yellow,
                Color.Blue,
                Color.Gray,
                Color.White
            ),

            )
    }
}


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ChapterSideSheet(
    chapters: List<ShortChapterModel>,
    isVisible: MutableState<Boolean>,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val offsetX by animateFloatAsState(
        targetValue = if (isVisible.value) 0f else screenWidth.toFloat(),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "BoxAnimation"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { isVisible.value = false }) {
        Box(
            modifier = Modifier
                .offset(x = offsetX.dp)
                .fillMaxSize()
                .padding(top = 52.dp, bottom = 24.dp, start = 92.dp)
                .shadow(
                    5.dp, shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 0.dp
                    )
                )
                .background(background)
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                item() {
                    Row(
                        Modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.toc),
                            style = labelText,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(painterResource(R.drawable.close),
                            "",
                            tint = accent_dark,
                            modifier = Modifier
                                .padding(17.dp)
                                .padding(8.dp)
                                .padding(vertical = 16.dp)
                                .clickable { isVisible.value = false })
                    }
                }
                items(chapters) {
                    Text(
                        text = it.name,
                        style = if (it.state != ChapterState.InProgress) detailsBody
                        else detailsSelectedBody,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.5.dp)
                    )
                }
            }
        }
    }
}


