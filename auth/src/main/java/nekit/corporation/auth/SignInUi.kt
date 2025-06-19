package nekit.corporation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import nekit.corporation.common_ui.theme.BookWorldTheme
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.appName
import nekit.corporation.common_ui.theme.signInSlogan
import nekit.corporation.common_ui.theme.white
import java.util.Locale

/*private const val DELAY_BETWEEN_SCROLL_MS = 10L
private const val SCROLL_DX = 1f*/

@Composable
fun SignInUi(component: SignInComponent) {
    val state by component.state.collectAsState(Dispatchers.Main.immediate)

    Column(
        Modifier
            .fillMaxSize()
            .background(accent_dark)
    ) {
        Spacer(Modifier.weight(0.112f))
        /* Box(Modifier.weight(0.3f)) {
             SingInCarousel(
                 state.carouselImages
             )
         }*/

        Spacer(Modifier.weight(0.043f))
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
        Spacer(Modifier.weight(0.016f))
        InputField(
            hint = stringResource(R.string.username),
            text = state.userName,
            error = state.userNameError,
            iconRes = state.nameIconRes,
            onValueChange = component::onNameChange,
            onImageClick = component::onNameImageClick,
        )
        //Spacer(Modifier.weight(0.016f))
        InputField(
            hint = stringResource(R.string.email),
            text = state.email,
            error = state.emailError,
            iconRes = state.emailIconRes,
            onValueChange = component::onEmailChange,
            onImageClick = component::onEmailImageClick,
        )
        //Spacer(Modifier.weight(0.0089f))
        InputField(
            hint = stringResource(R.string.password),
            text = state.password,
            error = state.passwordError,
            iconRes = state.passwordIconRes,
            onValueChange = component::onPasswordChange,
            onImageClick = component::onPasswordImageClick,
            visualTransformation = state.passwordImageTransformation
        )
       // Spacer(Modifier.weight(0.027f))
        ButtonSignIn(state.isButtonActive, component::onSignInClick)
        Spacer(Modifier.weight(0.054f))
    }
    if (state.inProgress) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SingInCarousel(images: ImmutableList<ImageBitmap>) {
    var infiniteList by remember { mutableStateOf(images) }

    val listState = rememberLazyListState()

    val imageSize = 270.dp

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

}*/
/*

tailrec возьму на заметку! топчик оптимизация
private tailrec suspend fun autoScroll(lazyListState: LazyListState) {
    lazyListState.scroll {
        scrollBy(SCROLL_DX)
    }
    delay(DELAY_BETWEEN_SCROLL_MS)

    autoScroll(lazyListState)
}
*/


@Composable
fun InputField(
    hint: String,
    text: String,
    error: Int?,
    iconRes: Int?,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    BookWorldTheme {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 16.dp)
                .testTag(hint),
            trailingIcon = {
                if (iconRes != null)
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = "",
                        modifier = Modifier
                            .alpha(if (text.isNotEmpty()) 1f else 0f)
                            .clickable(onClick = onImageClick)
                            .testTag("$hint icon"),
                        tint = accent_light
                    )

            },
            visualTransformation = visualTransformation,
            value = text,
            label = {
                if (text.isEmpty()) Text(
                    hint, style = MaterialTheme.typography.bodySmall
                )
            },
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = accent_light,
                unfocusedTextColor = accent_light,
                unfocusedBorderColor = accent_medium,
                focusedBorderColor = accent_medium,
            ),
            supportingText = {
                if (error != null) Text(
                    stringResource(error), style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                    )
                )
            }
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