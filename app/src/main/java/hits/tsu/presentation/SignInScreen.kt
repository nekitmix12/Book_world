package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hits.tsu.R
import hits.tsu.presentation.models.DeviceType
import hits.tsu.presentation.theme.BookWorldTheme
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_light
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.white
import java.util.Locale

//Pixel 2,PIXEL_4
@Composable
fun SignInScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(accent_dark)
    ) {
        Spacer(
            Modifier.size(
                when (getDeviceType()) {
                    DeviceType.ExtraLargePhone -> 78.dp
                    DeviceType.LargePhone -> 48.dp
                    DeviceType.MediumPhone -> 40.dp
                    DeviceType.SmallPhone -> 32.dp
                }
            )
        )
        SingInCarousel(
            listOf(
                ImageBitmap.imageResource(R.drawable.test_image2),
                ImageBitmap.imageResource(R.drawable.test_image4),
                ImageBitmap.imageResource(R.drawable.test_image2),
                ImageBitmap.imageResource(R.drawable.test_image4),
                ImageBitmap.imageResource(R.drawable.test_image2),
                ImageBitmap.imageResource(R.drawable.test_image4),
                ImageBitmap.imageResource(R.drawable.test_image2),
                ImageBitmap.imageResource(R.drawable.test_image4),
                ImageBitmap.imageResource(R.drawable.test_image2),
            )
        )
        Spacer(
            Modifier.size(
                when (getDeviceType()) {
                    DeviceType.ExtraLargePhone -> 48.dp
                    DeviceType.LargePhone -> 48.dp
                    DeviceType.MediumPhone -> 38.dp
                    DeviceType.SmallPhone -> 28.dp
                }
            )
        )
        LabelMedium()
        InputField(stringResource(R.string.email), listOf(R.drawable.close))
        Spacer(
            Modifier.size(
                when (getDeviceType()) {
                    DeviceType.ExtraLargePhone -> 8.dp
                    DeviceType.LargePhone -> 8.dp
                    DeviceType.MediumPhone -> 7.dp
                    DeviceType.SmallPhone -> 6.dp
                }
            )
        )
        InputField(stringResource(R.string.password), listOf(R.drawable.eye, R.drawable.not_see))
        Spacer(
            Modifier.size(
                when (getDeviceType()) {
                    DeviceType.ExtraLargePhone -> 24.dp
                    DeviceType.LargePhone -> 24.dp
                    DeviceType.MediumPhone -> 21.dp
                    DeviceType.SmallPhone -> 18.dp
                }
            )
        )
        ButtonSignIn(false)
    }

}

@Composable
fun getScreenWidthDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@Composable
fun getDeviceType(): DeviceType {
    val screenWidthDp = getScreenWidthDp()
    val screenHeightDp = getScreenHeightDp()
    return when {
        screenWidthDp < 360 && screenHeightDp < 640 -> DeviceType.SmallPhone
        screenWidthDp < 420 && screenHeightDp < 800 -> DeviceType.MediumPhone
        screenWidthDp >= 411 && screenHeightDp >= 914 -> DeviceType.ExtraLargePhone
        else -> DeviceType.LargePhone
    }
}

@Composable
fun getScreenHeightDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}

@Composable
@Stable
fun SingInCarousel(images: List<ImageBitmap>) {
    val infiniteList = remember { mutableStateListOf<ImageBitmap>() }

    infiniteList.addAll(images)

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        while (true) {
            listState.animateScrollBy(1f)
        }
    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            if (index >= infiniteList.size - 5) {
                infiniteList.addAll(images)
            }
        }
    }
    val imageSize = when (getDeviceType()) {
        DeviceType.ExtraLargePhone -> 270.dp
        DeviceType.LargePhone -> 270.dp
        DeviceType.MediumPhone -> 236.dp
        DeviceType.SmallPhone -> 202.dp
    }

    LazyRow(
        state = listState, modifier = Modifier.fillMaxWidth()
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
        }
    }

}

@Composable
fun LabelMedium() {
    BookWorldTheme {
        val bookSize = when (getDeviceType()) {
            DeviceType.ExtraLargePhone -> 96.sp
            DeviceType.LargePhone -> 96.sp
            DeviceType.MediumPhone -> 80.sp
            DeviceType.SmallPhone -> 64.sp
        }

        val openSize = when (getDeviceType()) {
            DeviceType.ExtraLargePhone -> 48.sp
            DeviceType.LargePhone -> 48.sp
            DeviceType.MediumPhone -> 40.sp
            DeviceType.SmallPhone -> 32.sp
        }

        val lineHeight = when (getDeviceType()) {
            DeviceType.ExtraLargePhone -> 76.8.sp
            DeviceType.LargePhone -> 76.8.sp
            DeviceType.MediumPhone -> 65.sp
            DeviceType.SmallPhone -> 52.sp
        }

        Column(
            Modifier
                .background(accent_dark)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                fontSize = openSize,
                text = stringResource(R.string.open_for_you).uppercase(Locale.ROOT),
                modifier = Modifier.fillMaxWidth(1f),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.book_world).uppercase(Locale.ROOT),
                fontSize = bookSize,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .offset(y = (-12).dp),
                lineHeight = lineHeight,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }

}


@Stable
@Composable
fun InputField(hint: String = "Электронная почта", iconRes: List<Int>) {
    BookWorldTheme {
        val isSeeAndClickable = remember { mutableStateOf(false) }
        val message = remember { mutableStateOf("1") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 16.dp),
            trailingIcon = {

                Icon(
                    painter = painterResource(
                        if (iconRes.size == 1 || isSeeAndClickable.value) iconRes[0] else iconRes[1]
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .alpha(if (message.value.isNotEmpty()) 1f else 0f)
                        .clickable {
                            if (iconRes.size == 1)
                                message.value = ""
                            else
                                isSeeAndClickable.value = !isSeeAndClickable.value
                        },
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
            onValueChange = { message.value = it },
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
fun ButtonSignIn(isActive: Boolean) {
    val paddingValues = when (getDeviceType()) {
        DeviceType.ExtraLargePhone -> 7.dp
        DeviceType.LargePhone -> 5.dp
        DeviceType.MediumPhone -> 3.dp
        DeviceType.SmallPhone -> 1.dp
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), colors = ButtonColors(
            contentColor = accent_dark,
            containerColor = white,
            disabledContentColor = accent_light,
            disabledContainerColor = accent_medium
        ), onClick = {}, enabled = isActive
    ) {
        Text(text = stringResource(R.string.sign_in), modifier = Modifier.padding(vertical = paddingValues))
    }
}