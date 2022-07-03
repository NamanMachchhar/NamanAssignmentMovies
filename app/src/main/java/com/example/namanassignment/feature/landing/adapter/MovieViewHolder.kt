package com.example.namanassignment.feature.landing.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.namanassignment.databinding.ItemMoviesBinding
import com.example.namanassignment.domain.model.MovieUI
import com.example.namanassignment.util.AppConstants


class MovieViewHolder(
    private val binding: ItemMoviesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieUI?, onMovieClicked: (MovieUI) -> Unit) {
        binding.tvMovieTitle.text = item?.title.orEmpty()
        binding.tvReleaseDate.text = AppConstants.getFormattedDate(item?.releaseDate.toString())
        Glide.with(binding.root)
            .load(item?.imagePosterUrl)
            .fitCenter()
            .into(binding.ivImageMovie)
        binding.root.setOnClickListener {
            item?.let {
                onMovieClicked.invoke(it)
            }
        }
    }
}