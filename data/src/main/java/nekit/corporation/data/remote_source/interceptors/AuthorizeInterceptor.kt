package nekit.corporation.data.remote_source.interceptors

import com.squareup.anvil.annotations.ContributesBinding
import nekit.corporation.common.AppScope
import nekit.corporation.data.local_source.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class AuthInterceptor @Inject constructor(private val tokenStorage: TokenStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = tokenStorage.getToken()
        return if (token != null) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}