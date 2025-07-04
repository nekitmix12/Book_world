package nekit.corporation.home_nav

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.bookmarks.BookmarksComponent
import nekit.corporation.common.AppScope
import nekit.corporation.home_nav.HomeComponent.BottomTab
import nekit.corporation.library.LibraryComponent
import nekit.corporation.search.SearchComponent

@ContributesAssistedFactory(AppScope::class, HomeComponent.Factory::class)
class HomeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val openChapter: HomeComponent.OpenChapter,
    @Assisted val openDetails: HomeComponent.OpenDetails,
    @Assisted val storeFactory: StoreFactory,
    private val libraryFactory: LibraryComponent.Factory,
    private val searchFactory: SearchComponent.Factory,
    private val bookmarksFactory: BookmarksComponent.Factory,
) : ComponentContext by componentContext, HomeComponent {
    private val navigation = SlotNavigation<BottomTab>()
    override val slot = childSlot(
        source = navigation,
        serializer = BottomTab.serializer(),
        key = NAVIGATION_SLOT_KEY,
        initialConfiguration = { BottomTab.LibraryChild },
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onTabSelected(tab: BottomTab) {
        navigation.activate(tab)
    }

    override fun onPlay() {
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
                    storeFactory = storeFactory,
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
                    methods = object : BookmarksComponent.Methods {
                        override fun goToChapter(chapterId: Long) {
                            openChapter.open(chapterId)
                        }

                        override fun goToDetails(detailsId: Long) {
                            openDetails.open(detailsId)
                        }
                    },
                    storeFactory = storeFactory
                )
            )
        }

    companion object {
        private const val NAVIGATION_SLOT_KEY = "HomeComponentImpl"
    }
}

