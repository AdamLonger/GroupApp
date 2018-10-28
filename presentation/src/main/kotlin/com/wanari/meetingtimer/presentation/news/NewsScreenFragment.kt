package com.wanari.meetingtimer.presentation.news

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.news.paging.NewsAdapter
import com.wanari.meetingtimer.presentation.news.paging.NewsDataSource
import com.wanari.meetingtimer.presentation.utils.paging.GenericPagedViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import model.NewsObject
import org.koin.android.ext.android.inject

class NewsScreenFragment : ScreenFragment<NewsScreenView, NewsViewState>(), NewsScreenView {
    override val initialViewState = NewsViewState()
    override val presenter by injectPresenter<NewsPresenter>()
    override val layoutRes = R.layout.fragment_news

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: GenericPagedViewModel<NewsObject>

    private val navigator by inject<Navigator>()

    override fun getTitle(context: Context): String = "News Screen"

    override fun render(viewState: NewsViewState) {
        viewState.dataSource?.let {
            initRecyclerView(it)
        }
    }

    private fun initRecyclerView(dataSource: NewsDataSource) {
        if (news_recycler_view.adapter != null &&
                context == null) return

        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        newsViewModel = GenericPagedViewModel(dataSource)
        newsAdapter = NewsAdapter(context!!)
        news_recycler_view.adapter = newsAdapter
        newsViewModel.getItems()?.observe(this, Observer(newsAdapter::submitList))
    }
}
