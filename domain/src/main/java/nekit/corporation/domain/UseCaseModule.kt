package nekit.corporation.domain

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import nekit.corporation.common.AppScope
import nekit.corporation.domain.usecases.UseCase

@Module
@ContributesTo(AppScope::class)
object UseCaseModule {
    @Provides
    fun provideConfiguration(): UseCase.Configuration =
        UseCase.Configuration(Dispatchers.Default)

}