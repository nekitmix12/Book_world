package nekit.corporation.details.chapter.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.authorText
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.labelText
import nekit.corporation.common_ui.theme.sliderText
import nekit.corporation.details.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterBottomSheet(
    component: BottomSheetComponent
) {
    val state by component.state.collectAsState()

    ModalBottomSheet(
        onDismissRequest = component::onDismiss, containerColor = nekit.corporation.common_ui.theme.background
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(text = stringResource(R.string.settings), style = nekit.corporation.common_ui.theme.labelText)
            Spacer(Modifier.height(28.dp))
            SettingSlider(
                stringResource(R.string.font_size),
                state.fontSize,
                component::onFontSizeChange
            )
            Spacer(Modifier.height(28.dp))
            SettingSlider(
                stringResource(R.string.string_space),
                state.sizeBetweenStrings,
                component::onSpaceChange
            )
            Spacer(Modifier.height(28.dp))

        }
    }
}

@Composable
fun SettingSlider(text: String, value: Float, onChange: (Float) -> Unit) {
    val steps = 9
    val textMaxWidth = (LocalConfiguration.current.screenWidthDp - 16 * 2) / (steps + 1)
    val interactionSource = remember { MutableInteractionSource() }
    var show by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect {
            show = true
        }
    }

    Column() {
        Text(text = text, style = nekit.corporation.common_ui.theme.authorText, modifier = Modifier.offset(y = (42).dp))
        Box(
            modifier = Modifier
                .offset(x = (textMaxWidth * (value - 9) - 22).dp)
                .clip(CircleShape)
                .background(if (show) nekit.corporation.common_ui.theme.accent_dark else Color.Transparent)
                .size(48.dp, 44.dp),
            contentAlignment = Alignment.Center
        ) {
            if (show)
                Text(
                    value.toInt().toString(),
                    style = nekit.corporation.common_ui.theme.sliderText
                )
        }
        Spacer(Modifier.height(4.dp))
        Slider(
            value = value,
            onValueChange = {
                onChange(it)
            },
            Modifier,
            steps = steps,
            valueRange = 9f..19f,
            onValueChangeFinished = {
                show = false
            },
            colors = SliderDefaults.colors(
                thumbColor = nekit.corporation.common_ui.theme.accent_dark,
                activeTrackColor = nekit.corporation.common_ui.theme.accent_dark,
                activeTickColor = nekit.corporation.common_ui.theme.accent_dark,
                inactiveTrackColor = nekit.corporation.common_ui.theme.accent_medium,
                disabledInactiveTickColor = nekit.corporation.common_ui.theme.accent_dark,
                disabledActiveTickColor = nekit.corporation.common_ui.theme.accent_dark,
                inactiveTickColor = nekit.corporation.common_ui.theme.accent_dark
            ),
            interactionSource = interactionSource
        )
    }

}