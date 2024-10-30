package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.MOVIE_STORY_DURATION
import com.example.moviecatalog.databinding.MoviesScreenBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.mappers.MovieAdapterMapper
import com.example.moviecatalog.presentation.view.adapter.FavoriteMovieAdapter
import com.example.moviecatalog.presentation.view.adapter.MovieAdapterTop
import com.example.moviecatalog.presentation.view.adapter.MovieCollectionRecyclerAdapter
import com.example.moviecatalog.presentation.view.adapter.RecyclerListener.FavoritesRecyclerScroller
import com.example.moviecatalog.presentation.view.adapter.RecyclerListener.MovieCollectionScroller
import com.example.moviecatalog.presentation.view.adapter.RecyclerListener.ScrollListener
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModel
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModelFactory
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class MovieScreen : Fragment(R.layout.movies_screen) {
    private lateinit var binding: MoviesScreenBinding
    private lateinit var viewModel: MovieScreenViewModel
    private lateinit var moviePage: List<MovieElementModelUI>

    private val movieTopAdapterMapper = MovieAdapterMapper()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, MovieScreenViewModelFactory())
            .get(MovieScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MoviesScreenBinding.bind(view)
        val randomMovieBtn = binding.randomMovie
        val progressBar = binding.progressBar
        val topMovieRecycler = binding.movieHorizontalRecyclerTop
        val adapterTop = MovieAdapterTop()
        val snapHelperTop = LinearSnapHelper()
        val snapHelperFavorites = LinearSnapHelper()
        val favoritesRecycler = binding.favoriteMoviesRecycler
        val movieCollectionRecycler = binding.movieCollectionRecycler


        randomMovieBtn.setOnClickListener {

        }
        lifecycleScope.launch {
            initMovieStoriesCarousel(
                topMovieRecycler,
                adapterTop,
                progressBar,
                snapHelperTop
            ).join()

            initFavorites(
                favoritesRecycler,
                snapHelperFavorites
            )

            initMovieCollection(movieCollectionRecycler)
        }
    }

    private fun initMovieStoriesCarousel(
        topMovieRecycler: RecyclerView,
        adapter: MovieAdapterTop,
        progressBar: StoriesProgressView,
        snapHelper: LinearSnapHelper
    ) =
        lifecycleScope.launch {
            getMoviePage(viewModel.randomMovie(1, 6)).join()
            subscribeMovieModel().join()
            initTopAdapter(topMovieRecycler, adapter, progressBar, snapHelper)
            startAutoScroll(topMovieRecycler, progressBar)
        }


    private fun initTopAdapter(
        topMovieRecycler: RecyclerView,
        adapter: MovieAdapterTop,
        progressBar: StoriesProgressView,
        snapHelper: LinearSnapHelper
    ) {

        topMovieRecycler.adapter = adapter
        topMovieRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.banPage(moviePage)
        adapter.data = movieTopAdapterMapper.map(moviePage)

        topMovieRecycler
            .addOnScrollListener(
                ScrollListener(
                    topMovieRecycler.layoutManager as LinearLayoutManager,
                    progressBar
                )
            )
        snapHelper.attachToRecyclerView(topMovieRecycler)

    }


    private fun subscribeMovieModel() = lifecycleScope.launch {
        viewModel.moviePage.take(1).collect { model ->
            if (model != null) {
                moviePage = model
            }
        }
    }

    private fun subscribeFavoriteMovies(adapter: FavoriteMovieAdapter) = lifecycleScope.launch {
        viewModel.favoriteMovies.collect { favorites ->
            if (favorites != null) {
                adapter.data = favorites
            }
        }
    }

    private fun startAutoScroll(recyclerView: RecyclerView, progressBar: StoriesProgressView) {
        recyclerView.adapter?.let { progressBar.setStoriesCount(it.itemCount) }
        progressBar.setStoryDuration(MOVIE_STORY_DURATION)
        progressBar.startStories()
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        progressBar.setStoriesListener(object : StoriesProgressView.StoriesListener {
            override fun onNext() {
                val nextItem =
                    (layoutManager.findFirstVisibleItemPosition() + 1) %
                            (recyclerView.adapter?.itemCount ?: 0)
                recyclerView.smoothScrollToPosition(nextItem)
            }

            override fun onPrev() {
                if (layoutManager.findFirstVisibleItemPosition() - 1 >= 0) {
                    recyclerView
                        .smoothScrollToPosition(
                            layoutManager.findFirstVisibleItemPosition() - 1
                        )
                }
            }

            override fun onComplete() {
                onNext()
            }
        })
    }

    private fun getMoviePage(page: Int) = lifecycleScope.launch {
        viewModel.getMoviePage(page).join()
    }

    private fun getFavorites() = lifecycleScope.launch {
        viewModel.getFavorites().join()
    }

    private fun initFavorites(
        recyclerView: RecyclerView,
        snapHelper: LinearSnapHelper
    ) {
        lifecycleScope.launch {
            getFavorites().join()
            initFavoritesRecycler(recyclerView, snapHelper)
        }
    }

    private fun initFavoritesRecycler(
        recyclerView: RecyclerView,
        snapHelper: LinearSnapHelper
    ) {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter =
            FavoriteMovieAdapter(recyclerView.layoutManager as LinearLayoutManager)
        recyclerView.addOnScrollListener(
            FavoritesRecyclerScroller(
                recyclerView.layoutManager as LinearLayoutManager
            )
        )
        snapHelper.attachToRecyclerView(recyclerView)
        subscribeFavoriteMovies(recyclerView.adapter as FavoriteMovieAdapter)
    }

    private fun initMovieCollection(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), 3, GridLayoutManager.VERTICAL, false
        )

        recyclerView.adapter =
            MovieCollectionRecyclerAdapter()
        subscribeMovieCollection(recyclerView.adapter as MovieCollectionRecyclerAdapter)

        recyclerView.addOnScrollListener(
            MovieCollectionScroller(
                recyclerView.layoutManager as GridLayoutManager,
                recyclerView.adapter as MovieCollectionRecyclerAdapter,
                viewModel
            )
        )
    }


    private fun subscribeMovieCollection(adapter: MovieCollectionRecyclerAdapter) =
        lifecycleScope.launch {
            viewModel.addNewPageToCollection(1).join()
            viewModel.movieCollection.collect { movieCollection ->
                if (movieCollection != null) {
                    adapter.data.addAll(movieCollection)
                    adapter.notifyDataSetChanged()
                }
            }
        }
}