package nekit.corporation.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nekit.corporation.common_ui.theme.BookWorldTheme
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.appName
import nekit.corporation.common_ui.theme.signInSlogan
import nekit.corporation.common_ui.theme.white
import java.util.Locale

private const val DELAY_BETWEEN_SCROLL_MS = 10L
private const val SCROLL_DX = 1f

@Composable
fun SignInUi(component: SignInComponent ) {
    val state = component.state.collectAsState(Dispatchers.Main.immediate)
    val isActive: SnapshotStateList<Boolean> = remember { mutableStateListOf(false, false) }


    Column(
        Modifier
            .fillMaxSize()
            .background(accent_dark)
    ) {
        Spacer(
            Modifier.weight(0.112f)
        )
        Box(Modifier.weight(0.3f)) {
            SingInCarousel(
                state.value.carouselImages
            )
        }

        Spacer(
            Modifier.weight(0.043f)
        )
        Text(
            text = stringResource(R.string.open_for_you).uppercase(Locale.ROOT),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("open_for_you"),
            style = signInSlogan
        )
        Text(
            text = stringResource(R.string.book_world).uppercase(Locale.ROOT),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-18).dp)
                .padding(horizontal = 16.dp)
                .testTag("book_world"),
            style = appName
        )
        Spacer(
            Modifier.weight(0.016f)
        )
        InputField(stringResource(R.string.email), listOf(R.drawable.close)) {
            isActive[0] = it
            Log.d("SignInScreen", "isActive: $isActive")
        }
        Spacer(
            Modifier.weight(0.0089f)
        )
        InputField(stringResource(R.string.password), listOf(R.drawable.eye, R.drawable.not_see), {
            isActive[1] = it
            Log.d("SignInScreen", "isActive: $isActive")
        })
        Spacer(
            Modifier.weight(0.027f)
        )
        ButtonSignIn(isActive.all {
            Log.d("SignInScreen", "it: $it")
            Log.d("SignInScreen", "isActive: $isActive")
            it
        }, component::onSignInClick)
        Spacer(
            Modifier.weight(0.054f)
        )
    }

}



@Composable
fun getScreenHeightDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@Stable
//код частично подрезан вот с этого сайта https://www.droidcon.com/2021/06/04/infinite-auto-scrolling-lists-with-recyclerview-lazylists-in-compose/
fun SingInCarousel(images: ImmutableList<ImageBitmap>) {
    var infiniteList by remember { mutableStateOf(images) }

    val listState = rememberLazyListState()

    val imageSize = 270.dp/*when (getDeviceType()) {
        DeviceType.ExtraLargePhone -> 270.dp
        DeviceType.LargePhone -> 270.dp
        DeviceType.MediumPhone -> 236.dp
        DeviceType.SmallPhone -> 202.dp
    }*/

    LazyRow(
        state = listState, modifier = Modifier.fillMaxWidth(), userScrollEnabled = false
    ) {


        items(infiniteList.size) { index ->
            Image(
                bitmap = infiniteList[index],
                contentDescription = null,
                modifier = Modifier
                    .height(imageSize)
                    .padding(horizontal = 4.dp),
                contentScale = ContentScale.FillHeight
            )
            if (infiniteList[index] == infiniteList.last()) {
                val currentList = infiniteList

                val secondPart = currentList.subList(0, listState.firstVisibleItemIndex)
                val firstPart =
                    currentList.subList(listState.firstVisibleItemIndex, currentList.size)

                rememberCoroutineScope().launch {
                    listState.scrollToItem(
                        0,
                        maxOf(0, listState.firstVisibleItemScrollOffset - SCROLL_DX.toInt())
                    )
                }

                infiniteList = (firstPart + secondPart) as ImmutableList<ImageBitmap>
            }
        }
    }

}

//tailrec возьму на заметку! топчик оптимизация
private tailrec suspend fun autoScroll(lazyListState: LazyListState) {
    lazyListState.scroll {
        scrollBy(SCROLL_DX)
    }
    delay(DELAY_BETWEEN_SCROLL_MS)

    autoScroll(lazyListState)
}


@Stable
@Composable
fun InputField(
    hint: String,
    iconRes: List<Int>,
    onValueChange: (Boolean) -> Unit,
) {
    BookWorldTheme {
        val isSeeAndClickable = remember { mutableStateOf(false) }
        val message = remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 16.dp)
                .testTag(hint),
            trailingIcon = {

                Icon(
                    painter = painterResource(
                        if (iconRes.size == 1 || isSeeAndClickable.value) iconRes[0] else iconRes[1]
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .alpha(if (message.value.isNotEmpty()) 1f else 0f)
                        .clickable {
                            if (iconRes.size == 1) message.value = ""
                            else isSeeAndClickable.value = !isSeeAndClickable.value
                        }
                        .testTag("$hint icon"),
                    tint = accent_light
                )

            },
            visualTransformation = if (iconRes.size != 1 && isSeeAndClickable.value) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            value = message.value,
            label = {
                if (message.value.isEmpty()) Text(
                    hint, style = MaterialTheme.typography.bodySmall
                )
            },
            onValueChange = {
                message.value = it
                Log.d("SignInScreen", "message: $message")
                Log.d("SignInScreen", "message.value: ${message.value == ""}")
                onValueChange(message.value != "")
            },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = accent_light,
                unfocusedTextColor = accent_light,
                unfocusedBorderColor = accent_medium,
                focusedBorderColor = accent_medium,
            )
        )

    }
}

@Composable
fun ButtonSignIn(isActive: Boolean, navigateToLibrary: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("signInButton"), colors = ButtonColors(
            contentColor = accent_dark,
            containerColor = white,
            disabledContentColor = accent_light,
            disabledContainerColor = accent_medium
        ), onClick = navigateToLibrary, enabled = isActive
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            modifier = Modifier.padding(vertical = 7.5.dp)
        )
    }
}