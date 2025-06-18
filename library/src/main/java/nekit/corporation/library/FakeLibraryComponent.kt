package nekit.corporation.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeLibraryComponent : LibraryComponent {
    override var state: StateFlow<LibraryState> = MutableStateFlow(
        LibraryState(
            newBooks = persistentListOf(),
            popularBooks = persistentListOf(),
        )
    )

    override fun onBookClick(bookId: String) {
    }
    @Preview(showSystemUi = true, device = Devices.PIXEL_7)
    @Composable
    fun LibraryUiPreview(){
        LibraryUi(FakeLibraryComponent())
    }
}
