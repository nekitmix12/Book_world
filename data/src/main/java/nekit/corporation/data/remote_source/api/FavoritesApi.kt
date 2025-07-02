package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.favorites.AddFavoriteRequestDto
import nekit.corporation.data.remote_source.dto.favorites.FavoritesDto
import nekit.corporation.domain.models.book.BookId
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritesApi {
    @GET("api/favorites")
    suspend fun getFavorites(): FavoritesDto

    @POST("api/favorites")
    suspend fun addFavorites(bookId: BookId): AddFavoriteRequestDto

    @DELETE("api/favorites/{id}")
    suspend fun deleteFavorites(@Path("id") documentBookId: String)
}