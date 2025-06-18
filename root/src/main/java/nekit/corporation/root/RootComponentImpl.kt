package nekit.corporation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.auth.SignInComponent
import nekit.corporation.auth.SignInComponentImpl
import nekit.corporation.common.AppScope
import nekit.corporation.details.chapter.chapter.ChapterComponent
import nekit.corporation.details.chapter.chapter.ChapterComponentImpl
import nekit.corporation.details.details.DetailsComponent
import nekit.corporation.details.details.DetailsComponentImpl
import nekit.corporation.home_nav.HomeComponent
import nekit.corporation.home_nav.HomeComponentImpl

@ContributesAssistedFactory(AppScope::class, RootComponent.Factory::class)
class RootComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val homeFactory: HomeComponent.Factory,
    private val signInFactory: SignInComponent.Factory,
    private val detailsFactory:  DetailsComponent.Factory,
    private val chapterComponent: ChapterComponent.Factory
) :
    ComponentContext by componentContext,
    RootComponent {
    private val navigation = StackNavigation<ChildConfig>()
    override val childStack = childStack(
        source = navigation,
        serializer = null,
        initialConfiguration = ChildConfig.SignIn,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: ChildConfig, componentContext: ComponentContext
    ): RootComponent.Child = when (config) {

        is ChildConfig.Chapter -> {
            RootComponent.Child.ChapterChild(
                chapterComponent(
                    componentContext = componentContext,
                    chapterId = config.chapterId,
                    onClose = navigation::pop
                )
            )
        }

        is ChildConfig.Details -> {
            RootComponent.Child.DetailsChild(
                detailsFactory(componentContext,
                    close = navigation::pop,
                    openChapter = { navigation.push(configuration = ChildConfig.Chapter(it)) }),
            )
        }

        is ChildConfig.SignIn -> {
            RootComponent.Child.AuthorizationChild(
                signInFactory(componentContext = componentContext,
                    onComplete = { navigation.push(ChildConfig.Home) })
            )
        }

        is ChildConfig.Home -> {
            RootComponent.Child.HomeChild(
                homeFactory(componentContext = componentContext,
                    openChapter = { navigation.push(configuration = ChildConfig.Chapter(it)) },
                    openDetails = { navigation.push(configuration = ChildConfig.Details(it)) })
            )
        }
    }

    @Parcelize
    sealed interface ChildConfig : Parcelable {

        @Parcelize
        data object SignIn : ChildConfig, Parcelable

        @Parcelize
        data class Chapter(val chapterId: String) : ChildConfig, Parcelable

        @Parcelize
        data class Details(val bookId: String) : ChildConfig, Parcelable

        @Parcelize
        data object Home : ChildConfig, Parcelable
    }
}