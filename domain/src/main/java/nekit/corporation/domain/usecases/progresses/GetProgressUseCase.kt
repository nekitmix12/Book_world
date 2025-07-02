package nekit.corporation.domain.usecases.progresses

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nekit.corporation.common.Result
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.models.progress.Progress
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetProgressUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
) {
    suspend fun process(): Flow<Result<Response>> = flow {
        tokenRefreshUseCase.process()
        val result = mutableListOf<Pair<Progress, ChaptersWithBookAndAuthor>>()
        val jobs = mutableListOf<Job>()
        val set = mutableSetOf<Long>()
        coroutineScope {
            repository.getProgress().data.forEach {
                if (!set.contains(it.chapterId)) {
                    set.add(it.chapterId)
                    jobs.add(launch(Dispatchers.IO) {
                        result.add(
                            it to repository.getBookByChapterId(it.chapterId)
                        )
                    })
                }
            }
        }
        jobs.forEach { it.join(); Log.d(TAG, it.key.toString()) }
        Log.i(TAG, "finish")
        emit(Response(result))
    }.map { Result.Success(it) as Result<Response> }
        .catch {
            emit(Result.Error(it.message.orEmpty()))
        }


    data class Response(val books: List<Pair<Progress, ChaptersWithBookAndAuthor>>) :
        UseCase.Response

    companion object {
        private const val TAG = "GetProgressUseCase"
    }
}