package com.example.namanassignment.domain.model

import com.squareup.moshi.Json

data class PopularMoviesResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val movies: List<Movie>?,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
) {
    val nextPage: Int
        get() = page + 1
}