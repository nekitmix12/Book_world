package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.progress.ProgressesDto
import nekit.corporation.data.remote_source.dto.progress.SaveProgressDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProgressApi {
    @GET("api/progresses")
    suspend fun getProgress(): ProgressesDto

    @POST("api/progresses")
    suspend fun saveProgress(@Body progress: SaveProgressDto): ProgressesDto

    @PUT("api/progresses")
    suspend fun editProgress(
        @Body progress: SaveProgressDto,
        @Path("") bookId: String
    ): ProgressesDto
}