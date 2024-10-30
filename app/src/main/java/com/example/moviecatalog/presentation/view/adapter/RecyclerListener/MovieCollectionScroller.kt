package com.example.moviecatalog.presentation.view.adapter.RecyclerListener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.presentation.view.adapter.MovieCollectionRecyclerAdapter
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModel

class MovieCollectionScroller(
    private val layoutManager: GridLayoutManager,
    private val adapter: MovieCollectionRecyclerAdapter,
    private val viewModel: MovieScreenViewModel,
    private var page: Int = 2
) :
    RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (layoutManager.findLastCompletelyVisibleItemPosition() ==
            adapter.data.lastIndex &&
            page != 6 && newState ==
            RecyclerView.SCREEN_STATE_ON
        ) {
            viewModel.addNewPageToCollection(page++)
        }
    }
}