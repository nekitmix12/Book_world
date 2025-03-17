package hits.tsu.presentation.models

import androidx.compose.ui.graphics.ImageBitmap

data class NewCarouselModel(
    val image: ImageBitmap,
    val description: String,
    val name: String,
)