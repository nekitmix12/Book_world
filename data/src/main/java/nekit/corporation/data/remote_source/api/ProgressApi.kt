package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.progress.Progresses
import nekit.corporation.data.remote_source.dto.progress.ProgressesDto
import nekit.corporation.data.remote_source.dto.progress.SaveProgress
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProgressApi {
    @GET("progresses")
    suspend  fun getProgress(): ProgressesDto

    @POST("progresses")
    suspend  fun saveProgress(@Body progress: SaveProgress): ProgressesDto

    @PUT("progresses")
    suspend fun editProgress(@Body progress: SaveProgress, @Path("") bookId:String): ProgressesDto
}