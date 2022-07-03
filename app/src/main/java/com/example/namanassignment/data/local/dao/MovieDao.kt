package com.example.namanassignment.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.namanassignment.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY api_page_index")
    fun getMoviesFlow(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun deleteAllPopularMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<MovieEntity>)

    @Transaction
    @Query("SELECT * FROM movies WHERE movies.id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}