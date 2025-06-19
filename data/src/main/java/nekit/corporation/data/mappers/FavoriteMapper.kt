package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.book.BookId
import nekit.corporation.data.remote_source.dto.favorites.AddFavoriteRequestDto
import nekit.corporation.data.remote_source.dto.favorites.Favorite
import nekit.corporation.data.remote_source.dto.favorites.FavoriteDto
import nekit.corporation.data.remote_source.dto.favorites.Favorites
import nekit.corporation.data.remote_source.dto.favorites.FavoritesDto
import nekit.corporation.domain.models.favorites.AddFavoriteRequest

fun FavoritesDto.toFavorites() = Favorites(data.map { it.toFavorite() }, meta.toMeta())

fun FavoriteDto.toFavorite() = Favorite(id, documentId, createdAt, updatedAt, publishedAt, bookId)

fun AddFavoriteRequestDto.toAddFavoriteRequest() = AddFavoriteRequest(data.toBookId())

fun BookId.toBookId() = nekit.corporation.domain.models.book.BookId(bookId)