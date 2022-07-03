package com.example.namanassignment.data.remote


import com.example.namanassignment.domain.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query(value = "page") page: Int?, @Query(value = "language") language: String): Response<PopularMoviesResponse>

}