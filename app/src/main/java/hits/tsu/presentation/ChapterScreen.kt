package hits.tsu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
    backgroundColor = 0xFFFCF2E8,
)
@Composable
fun ChapterScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        ChapterText()
    }
}

@Composable
fun ChapterText(){
    LazyColumn {

    }
}