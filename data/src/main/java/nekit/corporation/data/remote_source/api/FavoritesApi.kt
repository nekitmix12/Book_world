package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.favorites.AddFavoriteRequestDto
import nekit.corporation.data.remote_source.dto.favorites.FavoritesDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface FavoritesApi {
    @GET("favorites")
    fun getFavorites(): nekit.corporation.data.remote_source.dto.favorites.FavoritesDto

    @POST("favorites")
    fun addFavorites(): nekit.corporation.data.remote_source.dto.favorites.AddFavoriteRequestDto

    @DELETE("favorites")
    fun deleteFavorites()
}