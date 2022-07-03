package com.example.namanassignment.feature.detail

import androidx.lifecycle.ViewModel
import com.example.namanassignment.domain.use_case.DetailUseCase

class DetailViewModel(
    private val detailUseCase: DetailUseCase
) : ViewModel() {

    suspend fun getMovieById(movieId: Int) = detailUseCase.getMovieById(movieId)
}