package com.example.namanassignment.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.namanassignment.BuildConfig
import com.example.namanassignment.app.MainApp
import com.example.namanassignment.data.local.MovieDatabase
import com.example.namanassignment.data.local.entity.MovieEntity
import com.example.namanassignment.data.local.entity.RemoteKeyEntity
import com.example.namanassignment.data.mapper.mapToEntity
import com.example.namanassignment.data.remote.MovieService
import com.example.namanassignment.util.LocaleManager
import com.example.namanassignment.util.bodyOrThrow
import kotlinx.coroutines.CancellationException
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val database: MovieDatabase,
    private val service: MovieService
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByTitle(POPULAR_MOVIES_REMOTE_KEY)
                    }
                    remoteKey.nextPage
                }
            }
            Timber.d("Loading with load key: $loadKey, load type: $loadType")
            val data = service.getPopularMovies(loadKey, BuildConfig.LANG_EN).bodyOrThrow()
            val movies = data.movies.orEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteAllPopularMovies()
                    remoteKeyDao.deleteByTitle(POPULAR_MOVIES_REMOTE_KEY)
                }
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        title = POPULAR_MOVIES_REMOTE_KEY,
                        nextPage = data.nextPage
                    )
                )
                movieDao.insert(movies.map { it.mapToEntity(apiPageIndex = data.page) }.also {
                    Timber.d("Loaded movies: $it")
                })
            }
            return MediatorResult.Success(endOfPaginationReached = movies.isEmpty() || data.totalPages == data.page)
        } catch (e: CancellationException) {
            Timber.e(e)
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val POPULAR_MOVIES_REMOTE_KEY = "POPULAR_MOVIES_REMOTE_KEY"
    }
}