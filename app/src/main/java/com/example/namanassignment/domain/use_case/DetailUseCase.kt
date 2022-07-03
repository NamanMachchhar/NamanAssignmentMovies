package com.example.namanassignment.domain.use_case

import com.example.namanassignment.domain.repository.MovieRepository


class DetailUseCase(
    private val movieRepository: MovieRepository
) {

    suspend fun getMovieById(movieId: Int) = movieRepository.getMovieById(movieId = movieId)

}