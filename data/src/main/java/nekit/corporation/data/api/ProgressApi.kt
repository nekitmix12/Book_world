package nekit.corporation.data.api

import nekit.corporation.data.dto.progress.ProgressesDto
import nekit.corporation.data.dto.progress.SaveProgressDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface ProgressApi {
    @GET("progresses")
    fun getProgress(): ProgressesDto

    @POST("progresses")
    fun saveProgress(progress: SaveProgressDto): ProgressesDto

    @PUT("progresses")
    fun editProgress(progress: SaveProgressDto): ProgressesDto
}