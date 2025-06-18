package nekit.corporation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import nekit.corporation.auth.SignInComponent
import nekit.corporation.details.chapter.chapter.ChapterComponent
import nekit.corporation.details.details.DetailsComponent
import nekit.corporation.home_nav.HomeComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class AuthorizationChild(val component: SignInComponent) : Child
        class HomeChild(val component: HomeComponent) : Child
        class DetailsChild(val component: DetailsComponent) : Child
        class ChapterChild(val component: ChapterComponent) : Child
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext
        ): RootComponent
    }
}