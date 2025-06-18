package nekit.corporation.library

import androidx.compose.ui.graphics.ImageBitmap

data class NewCarouselModel(
    val id:String,
    val image: ImageBitmap,
    val description: String,
    val name: String,
)