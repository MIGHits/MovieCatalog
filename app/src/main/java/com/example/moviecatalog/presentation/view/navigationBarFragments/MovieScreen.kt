package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.MOVIE_STORY_DURATION
import com.example.moviecatalog.databinding.MoviesScreenBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.mappers.MovieAdapterMapper
import com.example.moviecatalog.presentation.view.adapter.MovieAdapterElement
import com.example.moviecatalog.presentation.view.adapter.MovieAdapterTop
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModel
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModelFactory
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.reflect.Field

class MovieScreen : Fragment(R.layout.movies_screen) {
    private lateinit var binding: MoviesScreenBinding
    private lateinit var viewModel: MovieScreenViewModel
    private lateinit var moviePage: List<MovieElementModelUI>

    private val movieMapper = MovieAdapterMapper()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, MovieScreenViewModelFactory())
            .get(MovieScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesScreenBinding.bind(view)

        val progressBar = binding.progressBar
        progressBar
        val topMovieRecycler = binding.movieHorizontalRecyclerTop
        val adapter = MovieAdapterTop()
        val snapHelper = LinearSnapHelper()

        lifecycleScope.launch {
            getMoviePage(1).join()
            subscribeMovieModel().join()
            initTopAdapter(topMovieRecycler, adapter, progressBar,snapHelper)
            startAutoScroll(topMovieRecycler, progressBar)
        }
    }

    private fun initTopAdapter(
        topMovieRecycler: RecyclerView,
        adapter: MovieAdapterTop,
        progressBar: StoriesProgressView,
        snapHelper: LinearSnapHelper) {

        topMovieRecycler.adapter = adapter
        topMovieRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter.data = movieMapper.map(moviePage)
        topMovieRecycler
            .addOnScrollListener(ScrollListener(
                topMovieRecycler.layoutManager as LinearLayoutManager,
                progressBar))
       snapHelper.attachToRecyclerView(topMovieRecycler)

    }


    private fun subscribeMovieModel() = lifecycleScope.launch {
        viewModel.moviePage.take(1).collect { model ->
            if (model != null) {
                moviePage = model
            }
        }
    }

    private fun startAutoScroll(recyclerView: RecyclerView, progressBar: StoriesProgressView) {
        recyclerView.adapter?.let { progressBar.setStoriesCount(it.itemCount) }
        progressBar.setStoryDuration(MOVIE_STORY_DURATION)
        progressBar.startStories()
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        progressBar.setStoriesListener(object : StoriesListener {
            override fun onNext() {
                val nextItem =
                    (layoutManager.findFirstVisibleItemPosition() + 1) %
                            (recyclerView.adapter?.itemCount ?: 0)
                recyclerView.smoothScrollToPosition(nextItem)
            }

            override fun onPrev() {
                if (layoutManager.findFirstVisibleItemPosition()-1>=0) {
                    recyclerView
                        .smoothScrollToPosition(
                            layoutManager.findFirstVisibleItemPosition() - 1)
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

}