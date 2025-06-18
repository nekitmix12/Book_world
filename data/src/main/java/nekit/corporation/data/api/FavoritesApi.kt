package nekit.corporation.data.api

import nekit.corporation.data.dto.favorites.AddFavoriteRequestDto
import nekit.corporation.data.dto.favorites.FavoritesDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

internal interface FavoritesApi {
    @GET("favorites")
    fun getFavorites(): FavoritesDto

    @POST("favorites")
    fun addFavorites(): AddFavoriteRequestDto

    @DELETE("favorites")
    fun
}