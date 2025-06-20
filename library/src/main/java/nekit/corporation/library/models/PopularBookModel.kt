package nekit.corporation.library.models

import androidx.compose.ui.graphics.ImageBitmap

data class PopularBookModel(
    val id: Long,
    val imageUrl: String,
    val author: String,
    val name: String,
)
