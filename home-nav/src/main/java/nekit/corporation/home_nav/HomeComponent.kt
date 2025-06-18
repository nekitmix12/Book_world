package nekit.corporation.home_nav

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import nekit.corporation.bookmarks.BookmarksComponent
import nekit.corporation.library.LibraryComponent
import nekit.corporation.search.SearchComponent

interface HomeComponent {
    val childStack: Value<ChildStack<*, BottomTabComponent>>
    fun onTabSelected(tab: BottomTab)
    fun onPlay()
    fun onOut()
    interface BottomTabComponent
    class LibraryChild(val component: LibraryComponent) : BottomTabComponent
    class SearchChild(val component: SearchComponent) : BottomTabComponent
    class BookmarksChild(val component: BookmarksComponent) : BottomTabComponent

    @Serializable
    sealed class BottomTab {
        @Serializable
        data object LibraryChild : BottomTab()

        @Serializable
        data object SearchChild : BottomTab()

        @Serializable
        data object BookmarksChild : BottomTab()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            openChapter: (String) -> Unit,
            openDetails: (String) -> Unit
        ): HomeComponent
    }
}