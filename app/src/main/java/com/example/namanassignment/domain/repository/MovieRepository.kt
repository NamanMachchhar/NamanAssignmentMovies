package com.example.namanassignment.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.namanassignment.data.local.MovieDatabase
import com.example.namanassignment.data.mapper.mapToUI
import com.example.namanassignment.data.paging.PageKeyedRemoteMediator
import com.example.namanassignment.data.remote.MovieService
import com.example.namanassignment.domain.model.MovieUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class MovieRepository(
    private val moviesDatabase: MovieDatabase,
    movieService: MovieService
) {

    val popularMoviesFlow: Flow<PagingData<MovieUI>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = PageKeyedRemoteMediator(moviesDatabase, movieService)
        ) {
            moviesDatabase.movieDao().getMoviesFlow()
        }.flow.map { it.map { entity -> entity.mapToUI() } }

    suspend fun getMovieById(movieId: Int): MovieUI? =
        moviesDatabase.movieDao().getMovieById(movieId = movieId)?.mapToUI()

    companion object {
        private const val PAGE_SIZE = 12
    }
}
