package nekit.corporation.home_nav

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.BookmarksComponent
import nekit.corporation.bookmarks.BookmarksComponentImpl
import nekit.corporation.common.AppScope
import nekit.corporation.home_nav.HomeComponent.BottomTab
import nekit.corporation.library.LibraryComponent
import nekit.corporation.search.SearchComponent

@ContributesAssistedFactory(AppScope::class, HomeComponent.Factory::class)
class HomeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val openDetails: HomeComponent.OpenDetails,
    @Assisted val openChapter: HomeComponent.OpenChapter,
    private val libraryFactory: LibraryComponent.Factory,
    private val searchFactory: SearchComponent.Factory,
    private val bookmarksFactory: BookmarksComponent.Factory,
) : ComponentContext by componentContext, HomeComponent {
    private val navigation = StackNavigation<BottomTab>()
    override val childStack = childStack(
        source = navigation,
        serializer = BottomTab.serializer(),
        initialConfiguration = BottomTab.LibraryChild,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onTabSelected(tab: BottomTab) {
        navigation.push(tab)
    }

    override fun onPlay() {
        TODO("Not yet implemented")
    }

    override fun onOut() {
        TODO("Not yet implemented")
    }

    private fun createChild(
        tab: BottomTab,
        context: ComponentContext
    ): HomeComponent.BottomTabComponent =
        when (tab) {
            BottomTab.LibraryChild -> HomeComponent.BottomTabComponent.LibraryChild(
                libraryFactory(
                    componentContext = context,
                    goToBook = openDetails::open,
                )
            )

            BottomTab.SearchChild -> HomeComponent.BottomTabComponent.SearchChild(
                searchFactory(
                    componentContext = context,
                    goToBook = openDetails::open
                )
            )

            BottomTab.BookmarksChild -> HomeComponent.BottomTabComponent.BookmarksChild(
                bookmarksFactory(
                    componentContext = context,
                    goToChapter = openChapter::open,
                    goToDetails = openDetails::open
                )
            )
        }


}

