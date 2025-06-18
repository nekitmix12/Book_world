package hits.tsu.presentation

import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.optional.SingleIn
import nekit.corporation.common.AppScope
import nekit.corporation.root.RootComponent


@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppDaggerComponent {
    val rootComponentFactory: RootComponent.Factory
}

