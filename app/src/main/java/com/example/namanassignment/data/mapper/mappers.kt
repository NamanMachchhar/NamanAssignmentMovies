package com.example.namanassignment.data.mapper


import com.example.namanassignment.data.local.entity.MovieEntity
import com.example.namanassignment.domain.model.Movie
import com.example.namanassignment.domain.model.MovieUI

fun MovieEntity.mapToUI(): MovieUI = MovieUI(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun Movie.mapToEntity(apiPageIndex: Int): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    apiPageIndex = apiPageIndex
)