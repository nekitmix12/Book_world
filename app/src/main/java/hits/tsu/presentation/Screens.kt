package hits.tsu.presentation

import kotlinx.serialization.Serializable

sealed class Screens() {
    @Serializable
    data object SignIn : Screens()

    @Serializable
    data object Library : Screens()

    @Serializable
    data class BookDetails(
        val bookId: String,
    ) : Screens()

    @Serializable
    data class Chapter(
        val chapterId: String,
    ) : Screens()

    @Serializable
    data object Bookmarks : Screens()

    @Serializable
    data object Search : Screens()
}

