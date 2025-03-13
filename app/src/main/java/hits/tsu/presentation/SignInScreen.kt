package hits.tsu.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScreen() {

}
@Composable
@Stable
fun SingInCarousel(images: List<Bitmap>) {
    val infiniteList = remember { mutableStateListOf<Bitmap>() }
    infiniteList.addAll(images)

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= infiniteList.size - 5) {
                    infiniteList.addAll(images)
                }
                if (index >= 5) {
                    infiniteList.removeAt(0)
                }
            }
    }

    LazyRow(state = listState) {
        items(infiniteList.size) { index ->
            Image(infiniteList[index].asImageBitmap(), "")
        }
    }
}