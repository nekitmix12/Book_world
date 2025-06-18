package nekit.corporation.common_ui.ui_kit

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import nekit.corporation.common_ui.theme.LibraryLabelText
import nekit.corporation.common_ui.theme.labelText

@Composable
fun MiddleLabel(label: String) {
    Text(
        text = label.uppercase(), style = labelText, modifier = Modifier.padding(horizontal = 16.dp).testTag("H2")
    )
}
@Composable
fun TopLabel(label: String) {
    Text(text = label.uppercase(), Modifier.padding(horizontal = 16.dp).testTag("библиотека"), style = LibraryLabelText)
}