package hits.tsu.presentation.models

import androidx.compose.ui.graphics.ImageBitmap

data class AuthorModel(
    val id: String,
    val img: ImageBitmap,
    val name: String,
)