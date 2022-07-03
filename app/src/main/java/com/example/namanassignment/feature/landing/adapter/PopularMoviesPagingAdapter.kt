package com.example.namanassignment.feature.landing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.namanassignment.databinding.ItemMoviesBinding
import com.example.namanassignment.domain.model.MovieUI

class PopularMoviesPagingAdapter(
    private val onMovieClicked: (MovieUI) -> Unit
) : PagingDataAdapter<MovieUI, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(item = getItem(position), onMovieClicked = onMovieClicked)
    }
}