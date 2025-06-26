package hits.tsu.presentation

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory
import nekit.corporation.common.AppScope
import nekit.corporation.root.RootComponent


@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppDaggerComponent {
    val rootComponentFactory: RootComponent.Factory
    @MergeComponent.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppDaggerComponent
    }
}

