package hits.tsu.presentation.models

import androidx.compose.ui.graphics.ImageBitmap

data class BookSearchModel (
    val id:String,
    val name:String,
    val author:String,
    val image: ImageBitmap
)