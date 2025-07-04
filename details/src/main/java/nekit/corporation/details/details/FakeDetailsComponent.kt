package nekit.corporation.details.details

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import nekit.corporation.details.models.ShortChapterModel

class FakeDetailsComponent : DetailsComponent {
    override val state = MutableStateFlow(DetailsStore.State(
        chapters =null/* persistentListOf(
            ShortChapterModel(1,"Глава 1"),
            ShortChapterModel(1,"Глава 3"),
            ShortChapterModel(1,"Глава 2"),
        )*/,
        name = "Код Давинчи",
        documentBookId = "",
        description = "Секретный код скрыт в работах Леонардо да Винчи...\n" +
                "Только он поможет найти христианские святыни, дающие немыслимые власть и могущество... \n" +
                "Ключ к величайшей тайне, над которой человечество билось веками, наконец может быть найден...",
        authorName = "Дэн Браун",
        readingPercent = 0.2f,
        inFavorite = false,
        loadFavorite = false,
        progress = 2,
        image = "",
        loading = false
    ))

    override fun onChapterClick(chapterId: Long) {
        TODO("Not yet implemented")
    }

    override fun onReadClick() {
        TODO("Not yet implemented")
    }

    override fun onFavoriteIconClick(isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }
}