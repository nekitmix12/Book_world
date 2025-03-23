package hits.tsu.presentation.models

import androidx.compose.ui.graphics.ImageBitmap

data class PopularBookModel(
    val id: String,
    val image: ImageBitmap,
    val author: String,
    val name: String,
)
