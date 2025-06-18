package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.progress.ProgressesDto
import nekit.corporation.data.remote_source.dto.progress.SaveProgressDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProgressApi {
    @GET("progresses")
    fun getProgress(): nekit.corporation.data.remote_source.dto.progress.ProgressesDto

    @POST("progresses")
    fun saveProgress(progress: nekit.corporation.data.remote_source.dto.progress.SaveProgressDto): nekit.corporation.data.remote_source.dto.progress.ProgressesDto

    @PUT("progresses")
    fun editProgress(progress: nekit.corporation.data.remote_source.dto.progress.SaveProgressDto, @Path("") bookId:String): nekit.corporation.data.remote_source.dto.progress.ProgressesDto
}