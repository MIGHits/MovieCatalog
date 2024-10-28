package com.example.moviecatalog.presentation.view.navigationBarFragments

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.common.Constants.MOVIE_STORY_DURATION
import jp.shts.android.storiesprogressview.StoriesProgressView

class FavoritesRecyclerScroller(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            applyTransformation(recyclerView)
        }
    }

    private fun applyTransformation(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val position = recyclerView.getChildAdapterPosition(child)

            if (position == firstCompletelyVisiblePosition) {
                child.scaleX = 1.07f
                child.scaleY = 1.07f
            } else {
                child.scaleX = 1.0f
                child.scaleY = 1.0f
            }
        }
    }
}
