package nekit.corporation.home_nav

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.BookmarksComponentImpl
import nekit.corporation.common.AppScope
import nekit.corporation.home_nav.HomeComponent.BottomTab
import nekit.corporation.library.LibraryComponentImpl
import nekit.corporation.search.SearchComponentImpl

@ContributesAssistedFactory(AppScope::class, HomeComponent.Factory::class)
class HomeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val openDetails: HomeComponent.OpenDetails,
    @Assisted val openChapter: HomeComponent.OpenChapter,
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
            BottomTab.LibraryChild -> HomeComponent.LibraryChild(
                LibraryComponentImpl(
                    componentContext = context
                )
            )

            BottomTab.SearchChild -> HomeComponent.SearchChild(
                SearchComponentImpl(
                    componentContext = context
                )
            )

            BottomTab.BookmarksChild -> HomeComponent.BookmarksChild(
                BookmarksComponentImpl(
                    componentContext = context
                )
            )
        }


}

