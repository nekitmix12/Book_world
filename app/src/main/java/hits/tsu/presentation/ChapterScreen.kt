package hits.tsu.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hits.tsu.R
import hits.tsu.presentation.models.ChapterModel
import hits.tsu.presentation.models.ChapterState
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
import hits.tsu.presentation.theme.white
import java.util.UUID


@Composable
fun ChapterScreen() {
    val bookName = "код да винчи"
    val chapterName = "Пролог"
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(0.1f))
        ChapterLabel(bookName, chapterName)
        Spacer(Modifier.height(16.dp))
        Box(Modifier.weight(1f)) {
            ChapterText()
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
        ChapterBottomBar()
    }
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
    text: List<String> = ("Париж, Лувр\u2002821.46\n" + "Знаменитый куратор Жак Соньер, пошатываясь, прошел под сводчатой аркой Большой галереи и устремился к первой попавшейся ему на глаза картине, полотну Караваджо. Ухватился руками за позолоченную раму и стал тянуть ее на себя, пока шедевр не сорвался со стены и не рухнул на семидесятилетнего старика Соньера, погребя его под собой.\n" + "Как и предполагал Соньер, неподалеку с грохотом опустилась " + "металлическая решетка, преграждающая доступ в этот зал. Паркетный пол содрогнулся. Где-то завыла сирена игнализации.\n" + "Несколько секунд куратор лежал неподвижно, хватая ртом " + "воздух и пытаясь сообразить, на каком свете находится. Я все еще жив. Потом он выполз из-под полотна и начал судорожно ози\u0002раться в поисках места, где можно спрятаться.\n" + "Голос прозвучал неожиданно близко:\n" + "— Не двигаться.\n" + "Стоявший на четвереньках куратор похолодел, потом медлен\u0002но обернулся. Всего в пятнадцати футах от него, за решеткой, высилась внушительная и грозная фигура его преследователя. Вы\u0002сокий, широкоплечий, с мертвенно-бледной кожей и редкими " + "белыми волосами. Белки розовые, а зрачки угрожающего темно\u0002красного цвета. Альбинос достал из кармана пистолет, сунул " + "длинный ствол в отверстие между железными прутьями и при\u0002целился в куратора.\n").split(
        "\n"
    ),
    textSize: Int = 14,
    spaceBetween: Int = 16,
) {


    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(text.size) {
            Text(
                text = text[it], style = chapterText, fontSize = textSize.sp
            )
            Spacer(Modifier.height(spaceBetween.dp))
        }
    }


}


@Composable
fun ChapterBottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painterResource(R.drawable.slide_back),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painterResource(R.drawable.more),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painterResource(R.drawable.next),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painterResource(R.drawable.settings),
                    contentDescription = "Localized description",
                    tint = white
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = accent_light,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {

                Icon(
                    painter = painterResource(if (true) R.drawable.stop else R.drawable.play),
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
    textSettingsModel: TextSettingsModel = TextSettingsModel(
        fontSize = 14, spaceBetween = 16
    ),
    onDismiss: (TextSettingsModel) -> Unit = {},
) {
    val textSettingsModelLocal = remember { textSettingsModel }
    ModalBottomSheet(
        onDismissRequest = { onDismiss(textSettingsModelLocal) }, containerColor = background
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
    backgroundColor = 0xFFFCF2E8,
)
@Composable
fun ChapterSideSheet(
    chapters: List<ChapterModel> = listOf(
        ChapterModel(
            UUID.randomUUID().toString(), "Факты", ChapterState.Passed
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Пролог", ChapterState.InProgress
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 1", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 2", ChapterState.NotWatch
        ),
        ChapterModel(
            UUID.randomUUID().toString(), "Глава 3", ChapterState.NotWatch
        ),
    ),
) {

    ModalDrawerSheet(
        modifier = Modifier
            .padding(bottom = 24.dp, top = 52.dp)
            .shadow(
                5.dp, shape = RoundedCornerShape(
                    topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp
                )
            ), drawerContainerColor = background, windowInsets = WindowInsets(0.dp)
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
                    Icon(
                        painterResource(R.drawable.close),
                        "",
                        tint = accent_dark,
                        modifier = Modifier
                            .padding(17.dp)
                            .padding(8.dp)
                            .padding(vertical = 16.dp)

                    )
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



