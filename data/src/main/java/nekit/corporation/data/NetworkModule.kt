package nekit.corporation.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import nekit.corporation.common.AppScope
import nekit.corporation.data.remote_source.api.AuthApi
import nekit.corporation.data.remote_source.api.BookApi
import nekit.corporation.data.remote_source.api.FavoritesApi
import nekit.corporation.data.remote_source.api.ProgressApi
import nekit.corporation.data.remote_source.api.QuotesApi
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@ContributesTo(AppScope::class)
object NetworkModule {

    @Provides
    @Reusable
    fun provideConnectionPool(): ConnectionPool = ConnectionPool(
        maxIdleConnections = 15,
        keepAliveDuration = 1,
        timeUnit = TimeUnit.MINUTES
    )

    @Provides
    @Reusable
    fun provideCache(context: Context): Cache {
        val cacheSize = 50 * 1024 * 1024
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, cacheSize.toLong())
    }

    @Provides
    @Reusable
    fun provideOkHttpClient(
        cache: Cache, pool: ConnectionPool, interceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder().readTimeout(10, TimeUnit.MINUTES)
            .cache(cache).connectionPool(pool).addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder().header("Cache-Control", "public, max-age=60").build()
            }.connectTimeout(10, TimeUnit.MINUTES).addInterceptor(interceptor).build()


    @Provides
    @Reusable
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://brilliant-delight-92875246ff.strapiapp.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient).build()


    @Provides
    @Reusable
    fun provideAuthService(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Reusable
    fun provideBookService(retrofit: Retrofit): BookApi = retrofit.create(
        BookApi::class.java
    )

    @Provides
    @Reusable
    fun provideFavoriteService(retrofit: Retrofit): FavoritesApi =
        retrofit.create(FavoritesApi::class.java)

    @Provides
    @Reusable
    fun provideProgressService(retrofit: Retrofit): ProgressApi =
        retrofit.create(ProgressApi::class.java)

    @Provides
    @Reusable
    fun provideQuotesService(retrofit: Retrofit): QuotesApi = retrofit.create(QuotesApi::class.java)
}