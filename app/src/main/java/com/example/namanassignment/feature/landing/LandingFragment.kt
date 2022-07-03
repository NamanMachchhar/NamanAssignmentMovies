package com.example.namanassignment.feature.landing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.namanassignment.R
import com.example.namanassignment.databinding.LandingFragmentBinding
import com.example.namanassignment.feature.landing.adapter.MovieLoadStateAdapter
import com.example.namanassignment.feature.landing.adapter.PopularMoviesPagingAdapter
import com.example.namanassignment.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import timber.log.Timber

class LandingFragment : Fragment(R.layout.landing_fragment), KoinComponent {

    private val binding by viewBinding(LandingFragmentBinding::bind)
    private val viewModel: LandingViewModel by viewModel()
    private val pagingAdapter = PopularMoviesPagingAdapter(onMovieClicked = { movie ->
        goToMovieDetails(movie.id)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.popularMoviesFlow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                try {
                    binding.popularMoviesSwipeRefreshLayout.isRefreshing =
                        it.refresh is LoadState.Loading
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun setupView() {
        binding.popularMoviesRecycler.apply {
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(retryListener = { pagingAdapter.retry() }),
                footer = MovieLoadStateAdapter(retryListener = { pagingAdapter.retry() })
            )
        }
        binding.popularMoviesSwipeRefreshLayout.setOnRefreshListener { pagingAdapter.refresh() }
    }


    private fun goToMovieDetails(movieId: Int) {
        findNavController().navigate(
            LandingFragmentDirections.actionLandingFragmentToDetailMovieFragment(movieId)
        )
    }
}