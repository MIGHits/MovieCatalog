package com.example.moviecatalog.presentation.view.navigationBarFragments

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.common.Constants.MOVIE_STORY_DURATION
import jp.shts.android.storiesprogressview.StoriesProgressView

class ScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val progressBar:StoriesProgressView) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
            layoutManager.findFirstCompletelyVisibleItemPosition()!=-1) {
            val snapPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            recyclerView.adapter?.let { progressBar.setStoriesCount(it.itemCount) }
            progressBar.setStoryDuration(MOVIE_STORY_DURATION)
            progressBar.startStories(snapPosition)
        }
    }
}