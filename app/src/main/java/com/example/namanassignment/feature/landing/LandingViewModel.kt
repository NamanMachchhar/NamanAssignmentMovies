package com.example.namanassignment.feature.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.namanassignment.domain.model.MovieUI
import com.example.namanassignment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class LandingViewModel(
    movieRepository: MovieRepository
) : ViewModel() {

    val popularMoviesFlow: Flow<PagingData<MovieUI>> =
        movieRepository.popularMoviesFlow.cachedIn(viewModelScope)
}