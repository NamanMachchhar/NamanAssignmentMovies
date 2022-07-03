package com.example.namanassignment.feature.landing.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.namanassignment.domain.model.MovieUI

class MovieDiffCallback : DiffUtil.ItemCallback<MovieUI>() {

    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem == newItem
    }
}