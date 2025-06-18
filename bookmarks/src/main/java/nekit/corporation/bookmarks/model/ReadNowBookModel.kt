package nekit.corporation.bookmarks.model

import androidx.compose.ui.graphics.ImageBitmap

data class ReadNowBookModel(
    val id: String,
    val image: ImageBitmap,
    val name: String,
    val part: String,
    val partInt: Float,
)
