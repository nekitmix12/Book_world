package nekit.corporation.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nekit.corporation.auth.SignInUi
import nekit.corporation.details.chapter.chapter.ChapterUi
import nekit.corporation.details.details.BookDetailUi
import nekit.corporation.home_nav.HomeUi

@Composable
fun RootUi(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(childStack) {
        when (val instance = it.instance) {
            is RootComponent.Child.HomeChild -> HomeUi(instance.component)
            is RootComponent.Child.ChapterChild -> ChapterUi(instance.component)
            is RootComponent.Child.DetailsChild -> BookDetailUi(instance.component)
            is RootComponent.Child.AuthorizationChild -> SignInUi(instance.component)
        }
    }
}
